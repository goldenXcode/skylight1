package skylight2.opengl;

import javax.microedition.khronos.opengles.GL10;

public class ModelBuffer extends GeometryBuffer {
	static final int DATA_ELEMENTS_PER_VERTEX = MODEL_COORDINATES_PER_VERTEX;

	static final int DATA_ELEMENTS_PER_TRIANGLE = VERTICES_PER_TRIANGLE * DATA_ELEMENTS_PER_VERTEX;

	static final int DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP = DATA_ELEMENTS_PER_VERTEX;

	public class IncompleteTrianglesGeometry {
		public IncompleteTrianglesGeometry addTriangle(float anX1, float aY1, float aZ1, float anX2, float aY2, float aZ2, float anX3, float aY3, float aZ3) {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_TRIANGLE;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			vertexDataAsArray[currentVertexDataIndex + 0] = (int) (anX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 1] = (int) (aY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 2] = (int) (aZ1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 3] = (int) (anX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 4] = (int) (aY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 5] = (int) (aZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 6] = (int) (anX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 7] = (int) (aY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 8] = (int) (aZ3 * (1 << 16));

			return incompleteTrianglesGeometry;
		}

		public IncompleteTrianglesGeometry skipTriangle() {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_TRIANGLE;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			return incompleteTrianglesGeometry;
		}

		public Geometry endGeometry() {
			final Geometry geometry = (Geometry) geometryStack.pop();
			geometry.setVertexDataLength(nextVertexDataIndex / DATA_ELEMENTS_PER_VERTEX - geometry.getVertexDataStartOffset());
			return geometry;
		}

		public IncompleteTrianglesGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(ModelBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLES));
			return incompleteTrianglesGeometry;
		}
	}

	public class InitialIncompleteTriangleStripGeometry {
		public IncompleteTriangleStripGeometry addTriangle(float anX1, float aY1, float aZ1, float anX2, float aY2, float aZ2, float anX3, float aY3, float aZ3) {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_TRIANGLE;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			vertexDataAsArray[currentVertexDataIndex + 0] = (int) (anX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 1] = (int) (aY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 2] = (int) (aZ1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 3] = (int) (anX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 4] = (int) (aY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 5] = (int) (aZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 6] = (int) (anX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 7] = (int) (aY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 8] = (int) (aZ3 * (1 << 16));

			return incompleteTriangleStripGeometry;
		}

		public InitialIncompleteTriangleStripGeometry skipTriangle() {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_TRIANGLE;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			return initialIncompleteTriangleStripGeometry;
		}
	}

	public class IncompleteTriangleStripGeometry {
		public IncompleteTriangleStripGeometry addTriangle(float anX1, float aY1, float aZ1) {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_VERTEX;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			vertexDataAsArray[currentVertexDataIndex + 0] = (int) (anX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 1] = (int) (aY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 2] = (int) (aZ1 * (1 << 16));

			return incompleteTriangleStripGeometry;
		}

		public IncompleteTriangleStripGeometry skipTriangle() {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_VERTEX;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			return incompleteTriangleStripGeometry;
		}

		public Geometry endGeometry() {
			final Geometry geometry = (Geometry) geometryStack.pop();
			geometry.setVertexDataLength(currentVertexDataIndex / DATA_ELEMENTS_PER_VERTEX - geometry.getVertexDataStartOffset());
			return geometry;
		}

		public IncompleteTriangleStripGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(ModelBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLE_STRIP));
			return incompleteTriangleStripGeometry;
		}
	}

	private final IncompleteTrianglesGeometry incompleteTrianglesGeometry = new IncompleteTrianglesGeometry();

	private final InitialIncompleteTriangleStripGeometry initialIncompleteTriangleStripGeometry = new InitialIncompleteTriangleStripGeometry();

	private final IncompleteTriangleStripGeometry incompleteTriangleStripGeometry = new IncompleteTriangleStripGeometry();

	public ModelBuffer(int aNumberOfVertices) {
		super(aNumberOfVertices, DATA_ELEMENTS_PER_VERTEX);
	}

	public IncompleteTrianglesGeometry startTrianglesGeometry() {
		if (!geometryStack.isEmpty()) {
			throw new RuntimeException(CORRECTLY_NESTED_EXCEPTION_MESSAGE);
		}
		geometryStack.push(new Geometry(this, nextVertexDataIndex, GL10.GL_TRIANGLES));
		return incompleteTrianglesGeometry;
	}

	public InitialIncompleteTriangleStripGeometry startTriangleStripGeometry() {
		if (!geometryStack.isEmpty()) {
			throw new RuntimeException(CORRECTLY_NESTED_EXCEPTION_MESSAGE);
		}
		geometryStack.push(new Geometry(this, nextVertexDataIndex, GL10.GL_TRIANGLE_STRIP));
		return initialIncompleteTriangleStripGeometry;
	}

	public void enable(GL10 aGL10) {
		aGL10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertexDataAsBuffer.position(0);
		aGL10.glVertexPointer(3, GL10.GL_FIXED, DATA_ELEMENTS_PER_TRIANGLE - VERTICES_PER_TRIANGLE * DATA_ELEMENTS_PER_VERTEX, vertexDataAsBuffer);
	}

	public void disable(GL10 aGL10) {
		aGL10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
