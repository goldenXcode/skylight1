package skylight2.opengl;

import javax.microedition.khronos.opengles.GL10;

public class ModelBuffer extends GeometryBuffer {
	static final int DATA_ELEMENTS_PER_TRIANGLE = VERTICES_PER_TRIANGLE * MODEL_COORDINATES_PER_VERTEX;

	static final int DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP = MODEL_COORDINATES_PER_VERTEX;

	public class IncompleteTrianglesGeometry {
		public IncompleteTrianglesGeometry addTriangle(float anX1, float aY1, float aZ1, float anX2, float aY2, float aZ2, float anX3, float aY3, float aZ3) {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_TRIANGLE > vertexDataAsArray.length) {
				growVertexData();
			}

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aZ1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aZ3 * (1 << 16));

			return incompleteTrianglesGeometry;
		}

		public IncompleteTrianglesGeometry skipTriangle() {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_TRIANGLE > vertexDataAsArray.length) {
				growVertexData();
			}

			currentVertexDataIndex += VERTICES_PER_TRIANGLE * MODEL_COORDINATES_PER_VERTEX;

			return incompleteTrianglesGeometry;
		}

		public Geometry endGeometry() {
			final Geometry geometry = (Geometry) geometryStack.pop();
			geometry.setVertexDataLength((currentVertexDataIndex - geometry.getVertexDataStartOffset()) / 3);
			return geometry;
		}

		public IncompleteTrianglesGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(ModelBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLES));
			return incompleteTrianglesGeometry;
		}
	}

	public class InitialIncompleteTriangleStripGeometry {
		public IncompleteTriangleStripGeometry addTriangle(float anX1, float aY1, float aZ1, float anX2, float aY2, float aZ2, float anX3, float aY3, float aZ3) {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_TRIANGLE > vertexDataAsArray.length) {
				growVertexData();
			}

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aZ1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aZ3 * (1 << 16));

			return incompleteTriangleStripGeometry;
		}

		public InitialIncompleteTriangleStripGeometry skipTriangle() {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_TRIANGLE > vertexDataAsArray.length) {
				growVertexData();
			}

			currentVertexDataIndex += VERTICES_PER_TRIANGLE * MODEL_COORDINATES_PER_VERTEX;

			return initialIncompleteTriangleStripGeometry;
		}
	}

	public class IncompleteTriangleStripGeometry {
		public IncompleteTriangleStripGeometry addTriangle(float anX1, float aY1, float aZ1) {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP > vertexDataAsArray.length) {
				growVertexData();
			}

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aZ1 * (1 << 16));

			return incompleteTriangleStripGeometry;
		}

		public IncompleteTriangleStripGeometry skipTriangle() {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP > vertexDataAsArray.length) {
				growVertexData();
			}

			currentVertexDataIndex += MODEL_COORDINATES_PER_VERTEX;

			return incompleteTriangleStripGeometry;
		}

		public Geometry endGeometry() {
			final Geometry geometry = (Geometry) geometryStack.pop();
			geometry.setVertexDataLength((currentVertexDataIndex - geometry.getVertexDataStartOffset()) / 3);
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
		super(aNumberOfVertices, MODEL_COORDINATES_PER_VERTEX);
	}

	public IncompleteTrianglesGeometry startTrianglesGeometry() {
		if (!geometryStack.isEmpty()) {
			throw new RuntimeException(CORRECTLY_NESTED_EXCEPTION_MESSAGE);
		}
		geometryStack.push(new Geometry(this, currentVertexDataIndex, GL10.GL_TRIANGLES));
		return incompleteTrianglesGeometry;
	}

	public InitialIncompleteTriangleStripGeometry startTriangleStripGeometry() {
		if (!geometryStack.isEmpty()) {
			throw new RuntimeException(CORRECTLY_NESTED_EXCEPTION_MESSAGE);
		}
		geometryStack.push(new Geometry(this, currentVertexDataIndex, GL10.GL_TRIANGLE_STRIP));
		return initialIncompleteTriangleStripGeometry;
	}

	public void enable(GL10 aGL10) {
		aGL10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertexDataAsBuffer.position(0);
		aGL10.glVertexPointer(3, GL10.GL_FIXED, DATA_ELEMENTS_PER_TRIANGLE - VERTICES_PER_TRIANGLE * MODEL_COORDINATES_PER_VERTEX, vertexDataAsBuffer);
	}

	public void disable(GL10 aGL10) {
		aGL10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
