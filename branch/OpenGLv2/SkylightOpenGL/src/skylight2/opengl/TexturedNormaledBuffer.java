package skylight2.opengl;

import javax.microedition.khronos.opengles.GL10;

public class TexturedNormaledBuffer extends Buffer {
	static final int DATA_ELEMENTS_PER_TRIANGLE =
			VERTICES_PER_TRIANGLE * (MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COMPONENTS_PER_VERTEX);

	static final int DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP = MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COMPONENTS_PER_VERTEX;

	public class IncompleteTrianglesGeometry {
		public IncompleteTrianglesGeometryTex addTriangle(int anX1, int aY1, int aZ1, int anX2, int aY2, int aZ2, int anX3, int aY3, int aZ3) {
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

			return incompleteTrianglesGeometryTex;
		}

		public IncompleteTrianglesGeometryTex skipTriangle() {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_TRIANGLE > vertexDataAsArray.length) {
				growVertexData();
			}

			currentVertexDataIndex += VERTICES_PER_TRIANGLE * MODEL_COORDINATES_PER_VERTEX;

			return incompleteTrianglesGeometryTex;
		}

		public Geometry endGeometry() {
			final Geometry geometry = (Geometry) geometryStack.pop();
			geometry.setVertexDataLength(geometry.getVertexDataStartOffset() - currentVertexDataIndex);
			return geometry;
		}

		public IncompleteTrianglesGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(TexturedNormaledBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLES));
			return incompleteTrianglesGeometry;
		}
	}

	public class IncompleteTrianglesGeometryTex {
		public IncompleteTrianglesGeometryNor setTextures(int aU1, int aV1, int aU2, int aV2, int aU3, int aV3) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV3 * (1 << 16));

			return incompleteTrianglesGeometryNor;
		}

		public IncompleteTrianglesGeometryNor skipTextures() {
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * TEXTURE_COORDINATES_PER_VERTEX;

			return incompleteTrianglesGeometryNor;
		}
	}

	public class IncompleteTrianglesGeometryNor {
		public IncompleteTrianglesGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1, float aNormalX2, float aNormalY2, float aNormalZ2,
				float aNormalX3, float aNormalY3, float aNormalZ3) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalZ1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalZ3 * (1 << 16));

			return incompleteTrianglesGeometry;
		}

		public IncompleteTrianglesGeometry skipNormals() {
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * NORMAL_COMPONENTS_PER_VERTEX;

			return incompleteTrianglesGeometry;
		}
	}

	public class InitialIncompleteTriangleStripGeometry {
		public InitialIncompleteTriangleStripGeometryTex addTriangle(int anX1, int aY1, int aZ1, int anX2, int aY2, int aZ2, int anX3, int aY3, int aZ3) {
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

			return initialIncompleteTriangleStripGeometryTex;
		}

		public InitialIncompleteTriangleStripGeometryTex skipTriangle() {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_TRIANGLE > vertexDataAsArray.length) {
				growVertexData();
			}

			currentVertexDataIndex += VERTICES_PER_TRIANGLE * MODEL_COORDINATES_PER_VERTEX;

			return initialIncompleteTriangleStripGeometryTex;
		}
	}

	public class InitialIncompleteTriangleStripGeometryTex {
		public InitialIncompleteTriangleStripGeometryNor setTextures(int aU1, int aV1, int aU2, int aV2, int aU3, int aV3) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV3 * (1 << 16));

			return initialIncompleteTriangleStripGeometryNor;
		}

		public InitialIncompleteTriangleStripGeometryNor skipTextures() {
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * TEXTURE_COORDINATES_PER_VERTEX;

			return initialIncompleteTriangleStripGeometryNor;
		}
	}

	public class InitialIncompleteTriangleStripGeometryNor {
		public IncompleteTriangleStripGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1, float aNormalX2, float aNormalY2, float aNormalZ2,
				float aNormalX3, float aNormalY3, float aNormalZ3) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalZ1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalX2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalY2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalZ2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalX3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalY3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalZ3 * (1 << 16));

			return incompleteTriangleStripGeometry;
		}

		public IncompleteTriangleStripGeometry skipNormals() {
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * NORMAL_COMPONENTS_PER_VERTEX;

			return incompleteTriangleStripGeometry;
		}
	}

	public class IncompleteTriangleStripGeometry {
		public IncompleteTriangleStripGeometryTex addTriangle(int anX1, int aY1, int aZ1) {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP > vertexDataAsArray.length) {
				growVertexData();
			}

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aZ1 * (1 << 16));

			return incompleteTriangleStripGeometryTex;
		}

		public IncompleteTriangleStripGeometryTex skipTriangle() {
			// grow the array if necessary
			if (currentVertexDataIndex + DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP > vertexDataAsArray.length) {
				growVertexData();
			}

			currentVertexDataIndex += MODEL_COORDINATES_PER_VERTEX;

			return incompleteTriangleStripGeometryTex;
		}

		public Geometry endGeometry() {
			final Geometry geometry = (Geometry) geometryStack.pop();
			geometry.setVertexDataLength(geometry.getVertexDataStartOffset() - currentVertexDataIndex);
			return geometry;
		}

		public IncompleteTriangleStripGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(TexturedNormaledBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLE_STRIP));
			return incompleteTriangleStripGeometry;
		}
	}

	public class IncompleteTriangleStripGeometryTex {
		public IncompleteTriangleStripGeometryNor setTextures(int aU1, int aV1) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV1 * (1 << 16));

			return incompleteTriangleStripGeometryNor;
		}

		public IncompleteTriangleStripGeometryNor skipTextures() {
			currentVertexDataIndex += TEXTURE_COORDINATES_PER_VERTEX;

			return incompleteTriangleStripGeometryNor;
		}
	}

	public class IncompleteTriangleStripGeometryNor {
		public IncompleteTriangleStripGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalX1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalY1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aNormalZ1 * (1 << 16));

			return incompleteTriangleStripGeometry;
		}

		public IncompleteTriangleStripGeometry skipNormals() {
			currentVertexDataIndex += NORMAL_COMPONENTS_PER_VERTEX;

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
		super(aNumberOfVertices, MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COMPONENTS_PER_VERTEX);
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
}
