package skylight2.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Stack;

import javax.microedition.khronos.opengles.GL10;

public abstract class GeometryBuffer {
	static final String CORRECTLY_NESTED_EXCEPTION_MESSAGE =
			"An existing geometry is still incomplete.  Nest geometries by calling startNestedGeometry() on the active geometry.";

	static final int BYTES_PER_INTEGER = Sizes.INTEGER_BIT_SIZE / Sizes.BYTE_BIT_SIZE;

	static final int MODEL_COORDINATES_PER_VERTEX = 3;

	static final int TEXTURE_COORDINATES_PER_VERTEX = 2;

	static final int NORMAL_COORDINATES_PER_VERTEX = 3;

	static final int COLOUR_PARTS_PER_VERTEX = 3;

	static final int VERTICES_PER_TRIANGLE = 3;

	private static final int GROWTH_FACTOR = 2;

	int[] vertexDataAsArray;

	int currentVertexDataIndex;

	protected IntBuffer vertexDataAsBuffer;

	/*
	 * Stack of nested geometries.
	 */
	protected final Stack geometryStack = new Stack();

	/**
	 * Package access constructor.
	 * 
	 * @param aNumberOfVertices
	 *            The number of vertices the buffer can contain.
	 * @param aIntsPerVertex
	 *            The number of ints (model coordinates = 3, normal = 3, texture coordinates = 2, and colors = 4)
	 *            required for each vertex.
	 */
	GeometryBuffer(int aNumberOfVertices, int aIntsPerVertex) {
		vertexDataAsArray = new int[aNumberOfVertices * aIntsPerVertex];
	}

	/**
	 * Flush any change to the buffer's geometries into the underlying NIO buffer, creating it the first time.
	 */
	public void flush() {
		// if the nio buffer has not been created yet, then create it
		if (vertexDataAsBuffer == null) {
			createVertexDataAsBuffer();
		}

		// point the geometryBuffer back to the beginning and write it to the nio buffer
		vertexDataAsBuffer.position(0);
		vertexDataAsBuffer.put(vertexDataAsArray);
	}

	/**
	 * Enable the client side state for this buffer.
	 */
	public abstract void enable(GL10 aGL10);

	/**
	 * Disable the client side state for this buffer.
	 */
	public abstract void disable(GL10 aGL10);

	/**
	 * Grow the vertex data array by some arbitrary, constant factor.
	 */
	protected void growVertexData() {
		final int[] newVertexDataAsArray = new int[vertexDataAsArray.length * GROWTH_FACTOR];
		System.arraycopy(vertexDataAsArray, 0, newVertexDataAsArray, 0, vertexDataAsArray.length);
		vertexDataAsArray = newVertexDataAsArray;
	}

	/**
	 * Creates a direct IntBuffer (in native byte order) and populates it with fixed point integers from a given list of
	 * Floats.
	 */
	private void createVertexDataAsBuffer() {
		// create a direct byte geometryBuffer in native byte order
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertexDataAsArray.length * Sizes.FLOAT_BIT_SIZE / Sizes.BYTE_BIT_SIZE);
		try {
			Class.forName("java.nio.ByteOrder");
			// TODO use reflection to run the next line... JSR 239 doesn't support byte order
			// but android requires it!
			// byteBuffer.order(ByteOrder.nativeOrder());
		} catch (ClassNotFoundException e) {
			// if ByteOrder does not exist, then
		}
		vertexDataAsBuffer = byteBuffer.asIntBuffer();
	}
}
