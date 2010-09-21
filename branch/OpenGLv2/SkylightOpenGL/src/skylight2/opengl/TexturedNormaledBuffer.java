package skylight2.opengl;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class TexturedNormaledBuffer extends GeometryBuffer {
	private static final int STRIDE = Sizes.BYTES_PER_INT * (MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX);

	static final int DATA_ELEMENTS_PER_TRIANGLE =
			VERTICES_PER_TRIANGLE * (MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX);

	static final int DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP = MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX;

	public class IncompleteTrianglesGeometry {
		public IncompleteTrianglesGeometryTex addTriangle(float anX1, float aY1, float aZ1, float anX2, float aY2, float aZ2, float anX3, float aY3, float aZ3) {
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

			vertexDataAsArray[currentVertexDataIndex + 8] = (int) (anX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 9] = (int) (aY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 10] = (int) (aZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 16] = (int) (anX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 17] = (int) (aY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 18] = (int) (aZ3 * (1 << 16));

			return incompleteTrianglesGeometryTex;
		}

		public IncompleteTrianglesGeometryTex skipTriangle() {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_TRIANGLE;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			return incompleteTrianglesGeometryTex;
		}

		public Geometry endGeometry() {
			final Geometry geometry = (Geometry) geometryStack.pop();
			geometry.setVertexDataLength(nextVertexDataIndex / (TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX + MODEL_COORDINATES_PER_VERTEX)
					- geometry.getVertexDataStartOffset());
			return geometry;
		}

		public IncompleteTrianglesGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(TexturedNormaledBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLES));
			return incompleteTrianglesGeometry;
		}
	}

	public class IncompleteTrianglesGeometryTex {
		public IncompleteTrianglesGeometryNor setTextures(float aU1, float aV1, float aU2, float aV2, float aU3, float aV3) {
			vertexDataAsArray[currentVertexDataIndex + 3] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 4] = (int) (aV1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 11] = (int) (aU2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 12] = (int) (aV2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 19] = (int) (aU3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 20] = (int) (aV3 * (1 << 16));

			return incompleteTrianglesGeometryNor;
		}

		public IncompleteTrianglesGeometryNor skipTextures() {
			return incompleteTrianglesGeometryNor;
		}
	}

	public class IncompleteTrianglesGeometryNor {
		public IncompleteTrianglesGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1, float aNormalX2, float aNormalY2, float aNormalZ2,
				float aNormalX3, float aNormalY3, float aNormalZ3) {
			vertexDataAsArray[currentVertexDataIndex + 5] = (int) (aNormalX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 6] = (int) (aNormalY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 7] = (int) (aNormalZ1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 13] = (int) (aNormalX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 14] = (int) (aNormalY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 15] = (int) (aNormalZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 21] = (int) (aNormalX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 22] = (int) (aNormalY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 23] = (int) (aNormalZ3 * (1 << 16));

			return incompleteTrianglesGeometry;
		}

		public IncompleteTrianglesGeometry skipNormals() {
			return incompleteTrianglesGeometry;
		}
	}

	public class InitialIncompleteTriangleStripGeometry {
		public InitialIncompleteTriangleStripGeometryTex addTriangle(float anX1, float aY1, float aZ1, float anX2, float aY2, float aZ2, float anX3, float aY3,
				float aZ3) {
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

			vertexDataAsArray[currentVertexDataIndex + 8] = (int) (anX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 9] = (int) (aY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 10] = (int) (aZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 16] = (int) (anX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 17] = (int) (aY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 18] = (int) (aZ3 * (1 << 16));

			return initialIncompleteTriangleStripGeometryTex;
		}

		public InitialIncompleteTriangleStripGeometryTex skipTriangle() {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_TRIANGLE;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			return initialIncompleteTriangleStripGeometryTex;
		}
	}

	public class InitialIncompleteTriangleStripGeometryTex {
		public InitialIncompleteTriangleStripGeometryNor setTextures(float aU1, float aV1, float aU2, float aV2, float aU3, float aV3) {
			vertexDataAsArray[currentVertexDataIndex + 3] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 4] = (int) (aV1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 11] = (int) (aU2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 12] = (int) (aV2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 19] = (int) (aU3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 20] = (int) (aV3 * (1 << 16));

			return initialIncompleteTriangleStripGeometryNor;
		}

		public InitialIncompleteTriangleStripGeometryNor skipTextures() {
			return initialIncompleteTriangleStripGeometryNor;
		}
	}

	public class InitialIncompleteTriangleStripGeometryNor {
		public IncompleteTriangleStripGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1, float aNormalX2, float aNormalY2, float aNormalZ2,
				float aNormalX3, float aNormalY3, float aNormalZ3) {
			vertexDataAsArray[currentVertexDataIndex + 5] = (int) (aNormalX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 6] = (int) (aNormalY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 7] = (int) (aNormalZ1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 13] = (int) (aNormalX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 14] = (int) (aNormalY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 15] = (int) (aNormalZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex + 21] = (int) (aNormalX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 22] = (int) (aNormalY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 23] = (int) (aNormalZ3 * (1 << 16));

			return incompleteTriangleStripGeometry;
		}

		public IncompleteTriangleStripGeometry skipNormals() {
			return incompleteTriangleStripGeometry;
		}
	}

	public class IncompleteTriangleStripGeometry {
		public IncompleteTriangleStripGeometryTex addTriangle(float anX1, float aY1, float aZ1) {
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

			return incompleteTriangleStripGeometryTex;
		}

		public IncompleteTriangleStripGeometryTex skipTriangle() {
			// move the current index on to the next
			currentVertexDataIndex = nextVertexDataIndex;
			nextVertexDataIndex += DATA_ELEMENTS_PER_TRIANGLE;

			// grow the array if necessary
			if (nextVertexDataIndex > vertexDataAsArray.length) {
				growVertexData();
			}

			return incompleteTriangleStripGeometryTex;
		}

		public Geometry endGeometry() {
			final Geometry geometry = (Geometry) geometryStack.pop();
			geometry.setVertexDataLength(nextVertexDataIndex / (TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX + MODEL_COORDINATES_PER_VERTEX)
					- geometry.getVertexDataStartOffset());
			return geometry;
		}

		public IncompleteTriangleStripGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(TexturedNormaledBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLE_STRIP));
			return incompleteTriangleStripGeometry;
		}
	}

	public class IncompleteTriangleStripGeometryTex {
		public IncompleteTriangleStripGeometryNor setTextures(float aU1, float aV1) {
			vertexDataAsArray[currentVertexDataIndex + 3] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 4] = (int) (aV1 * (1 << 16));

			return incompleteTriangleStripGeometryNor;
		}

		public IncompleteTriangleStripGeometryNor skipTextures() {
			return incompleteTriangleStripGeometryNor;
		}
	}

	public class IncompleteTriangleStripGeometryNor {
		public IncompleteTriangleStripGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1) {
			vertexDataAsArray[currentVertexDataIndex + 5] = (int) (aNormalX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 6] = (int) (aNormalY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex + 7] = (int) (aNormalZ1 * (1 << 16));

			return incompleteTriangleStripGeometry;
		}

		public IncompleteTriangleStripGeometry skipNormals() {
			return incompleteTriangleStripGeometry;
		}
	}

	private final IncompleteTrianglesGeometry incompleteTrianglesGeometry = new IncompleteTrianglesGeometry();

	private final IncompleteTrianglesGeometryTex incompleteTrianglesGeometryTex = new IncompleteTrianglesGeometryTex();

	private final IncompleteTrianglesGeometryNor incompleteTrianglesGeometryNor = new IncompleteTrianglesGeometryNor();

	private final InitialIncompleteTriangleStripGeometry initialIncompleteTriangleStripGeometry = new InitialIncompleteTriangleStripGeometry();

	private final InitialIncompleteTriangleStripGeometryTex initialIncompleteTriangleStripGeometryTex = new InitialIncompleteTriangleStripGeometryTex();

	private final InitialIncompleteTriangleStripGeometryNor initialIncompleteTriangleStripGeometryNor = new InitialIncompleteTriangleStripGeometryNor();

	private final IncompleteTriangleStripGeometry incompleteTriangleStripGeometry = new IncompleteTriangleStripGeometry();

	private final IncompleteTriangleStripGeometryTex incompleteTriangleStripGeometryTex = new IncompleteTriangleStripGeometryTex();

	private final IncompleteTriangleStripGeometryNor incompleteTriangleStripGeometryNor = new IncompleteTriangleStripGeometryNor();

	public TexturedNormaledBuffer(int aNumberOfVertices) {
		super(aNumberOfVertices, MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX);
	}

	public IncompleteTrianglesGeometry startTrianglesGeometry() {
		if (!geometryStack.isEmpty()) {
			throw new RuntimeException(CORRECTLY_NESTED_EXCEPTION_MESSAGE);
		}
		geometryStack.push(new Geometry(this, nextVertexDataIndex
				/ (TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX + MODEL_COORDINATES_PER_VERTEX), GL10.GL_TRIANGLES));
		return incompleteTrianglesGeometry;
	}

	public InitialIncompleteTriangleStripGeometry startTriangleStripGeometry() {
		if (!geometryStack.isEmpty()) {
			throw new RuntimeException(CORRECTLY_NESTED_EXCEPTION_MESSAGE);
		}
		geometryStack.push(new Geometry(this, nextVertexDataIndex
				/ (TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX + MODEL_COORDINATES_PER_VERTEX), GL10.GL_TRIANGLE_STRIP));
		return initialIncompleteTriangleStripGeometry;
	}

	public void enable(GL10 aGL10) {
		aGL10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertexDataAsBuffer.position(0);
		aGL10.glVertexPointer(MODEL_COORDINATES_PER_VERTEX, GL10.GL_FIXED, STRIDE, vertexDataAsBuffer);

		aGL10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		vertexDataAsBuffer.position(MODEL_COORDINATES_PER_VERTEX);
		final IntBuffer texCoordBuffer = vertexDataAsBuffer.slice();
		aGL10.glTexCoordPointer(TEXTURE_COORDINATES_PER_VERTEX, GL10.GL_FIXED, STRIDE, texCoordBuffer);

		aGL10.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		vertexDataAsBuffer.position(MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX);
		final IntBuffer normalBuffer = vertexDataAsBuffer.slice();
		aGL10.glNormalPointer(GL10.GL_FIXED, STRIDE, normalBuffer);
	}

	public void disable(GL10 aGL10) {
		aGL10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		aGL10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		aGL10.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
}
