package skylight2.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Stack;

public class GeometryGroup {
	static final String CORRECTLY_NESTED_EXCEPTION_MESSAGE =
			"An existing geometry is still incomplete.  Nest geometries by calling startNestedGeometry() on the active geometry.";

	static final int BYTES_PER_INTEGER = Integer.SIZE / Byte.SIZE;

	static final int MODEL_COORDINATES_PER_VERTEX = 3;

	static final int TEXTURE_COORDINATES_PER_VERTEX = 2;

	static final int NORMAL_COMPONENTS_PER_VERTEX = 3;

	static final int COLOUR_PARTS_PER_VERTEX = 3;

	static final int VERTICES_PER_TRIANGLE = 3;

	int[] vertexDataAsArray;

	int currentVertexDataIndex;

	IntBuffer vertexDataAsBuffer;

	Stack<Geometry> geometryStack = new Stack<Geometry>();

	public GeometryGroup(int aNumberOfVertices, int aIntsPerVertex) {
		vertexDataAsArray = new int[aNumberOfVertices * aIntsPerVertex];
	}

	public void flush() {
		if (vertexDataAsBuffer == null) {
			createVertexDataAsBuffer();
		}

		// point the buffer back to the beginning
		vertexDataAsBuffer.position(0);
		vertexDataAsBuffer.put(vertexDataAsArray);
	}

	/**
	 * Creates a direct IntBuffer (in native byte order) and populates it with fixed point integers from a given list of
	 * Floats.
	 */
	private void createVertexDataAsBuffer() {
		// create a direct byte buffer in native byte order
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertexDataAsArray.length * Float.SIZE / Byte.SIZE);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexDataAsBuffer = byteBuffer.asIntBuffer();
	}

	void growVertexData() {
		final int[] newVertexDataAsArray = new int[vertexDataAsArray.length * 4];
		System.arraycopy(vertexDataAsArray, 0, newVertexDataAsArray, 0, vertexDataAsArray.length);
		vertexDataAsArray = newVertexDataAsArray;
	}
}
