package skylight2.opengl;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class TexturedColoredNormaledBuffer extends GeometryBuffer {
	static final int DATA_ELEMENTS_PER_TRIANGLE =
			VERTICES_PER_TRIANGLE * (MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + COLOUR_PARTS_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX);

	static final int DATA_ELEMENTS_PER_EXTRA_TRIANGLE_IN_STRIP =
			MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + COLOUR_PARTS_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX;

	public class IncompleteTrianglesGeometry {
		public IncompleteTrianglesGeometryTex addTriangle(float anX1, float aY1, float aZ1, float anX2, float aY2, float aZ2, float anX3, float aY3, float aZ3) {
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
			geometry.setVertexDataLength(currentVertexDataIndex - geometry.getVertexDataStartOffset());
			return geometry;
		}

		public IncompleteTrianglesGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(TexturedColoredNormaledBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLES));
			return incompleteTrianglesGeometry;
		}
	}

	public class IncompleteTrianglesGeometryTex {
		public IncompleteTrianglesGeometryCol setTextures(float aU1, float aV1, float aU2, float aV2, float aU3, float aV3) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV3 * (1 << 16));

			return incompleteTrianglesGeometryCol;
		}

		public IncompleteTrianglesGeometryCol skipTextures() {
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * TEXTURE_COORDINATES_PER_VERTEX;

			return incompleteTrianglesGeometryCol;
		}
	}

	public class IncompleteTrianglesGeometryCol {
		public IncompleteTrianglesGeometryNor setColors(float anR1, float aG1, float aB1, float anR2, float aG2, float aB2, float anR3, float aG3, float aB3) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (anR1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aG1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aB1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anR2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aG2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aB2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anR3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aG3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aB3 * (1 << 16));

			return incompleteTrianglesGeometryNor;
		}

		public IncompleteTrianglesGeometryNor skipColors() {
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * COLOUR_PARTS_PER_VERTEX;

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
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * NORMAL_COORDINATES_PER_VERTEX;

			return incompleteTrianglesGeometry;
		}
	}

	public class InitialIncompleteTriangleStripGeometry {
		public InitialIncompleteTriangleStripGeometryTex addTriangle(float anX1, float aY1, float aZ1, float anX2, float aY2, float aZ2, float anX3, float aY3,
				float aZ3) {
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
		public InitialIncompleteTriangleStripGeometryCol setTextures(float aU1, float aV1, float aU2, float aV2, float aU3, float aV3) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV3 * (1 << 16));

			return initialIncompleteTriangleStripGeometryCol;
		}

		public InitialIncompleteTriangleStripGeometryCol skipTextures() {
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * COLOUR_PARTS_PER_VERTEX;

			return initialIncompleteTriangleStripGeometryCol;
		}
	}

	public class InitialIncompleteTriangleStripGeometryCol {
		public InitialIncompleteTriangleStripGeometryNor setColors(float anR1, float aG1, float aB1, float anR2, float aG2, float aB2, float anR3, float aG3,
				float aB3) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (anR1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aG1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aB1 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anR2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aG2 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aB2 * (1 << 16));

			vertexDataAsArray[currentVertexDataIndex++] = (int) (anR3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aG3 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aB3 * (1 << 16));

			return initialIncompleteTriangleStripGeometryNor;
		}

		public InitialIncompleteTriangleStripGeometryNor skipColors() {
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * COLOUR_PARTS_PER_VERTEX;

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
			currentVertexDataIndex += VERTICES_PER_TRIANGLE * NORMAL_COORDINATES_PER_VERTEX;

			return incompleteTriangleStripGeometry;
		}
	}

	public class IncompleteTriangleStripGeometry {
		public IncompleteTriangleStripGeometryTex addTriangle(float anX1, float aY1, float aZ1) {
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
			geometry.setVertexDataLength(currentVertexDataIndex - geometry.getVertexDataStartOffset());
			return geometry;
		}

		public IncompleteTriangleStripGeometry startNestedGeometry() {
			geometryStack.push(new Geometry(TexturedColoredNormaledBuffer.this, currentVertexDataIndex, GL10.GL_TRIANGLE_STRIP));
			return incompleteTriangleStripGeometry;
		}
	}

	public class IncompleteTriangleStripGeometryTex {
		public IncompleteTriangleStripGeometryCol setTextures(float aU1, float aV1) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aU1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aV1 * (1 << 16));

			return incompleteTriangleStripGeometryCol;
		}

		public IncompleteTriangleStripGeometryCol skipTextures() {
			currentVertexDataIndex += TEXTURE_COORDINATES_PER_VERTEX;

			return incompleteTriangleStripGeometryCol;
		}
	}

	public class IncompleteTriangleStripGeometryCol {
		public IncompleteTriangleStripGeometryNor setColors(float anR1, float aG1, float aB1) {
			vertexDataAsArray[currentVertexDataIndex++] = (int) (anR1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aG1 * (1 << 16));
			vertexDataAsArray[currentVertexDataIndex++] = (int) (aB1 * (1 << 16));

			return incompleteTriangleStripGeometryNor;
		}

		public IncompleteTriangleStripGeometryNor skipColors() {
			currentVertexDataIndex += COLOUR_PARTS_PER_VERTEX;

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
			currentVertexDataIndex += NORMAL_COORDINATES_PER_VERTEX;

			return incompleteTriangleStripGeometry;
		}
	}

	private final IncompleteTrianglesGeometry incompleteTrianglesGeometry = new IncompleteTrianglesGeometry();

	private final IncompleteTrianglesGeometryTex incompleteTrianglesGeometryTex = new IncompleteTrianglesGeometryTex();

	private final IncompleteTrianglesGeometryCol incompleteTrianglesGeometryCol = new IncompleteTrianglesGeometryCol();

	private final IncompleteTrianglesGeometryNor incompleteTrianglesGeometryNor = new IncompleteTrianglesGeometryNor();

	private final InitialIncompleteTriangleStripGeometry initialIncompleteTriangleStripGeometry = new InitialIncompleteTriangleStripGeometry();

	private final InitialIncompleteTriangleStripGeometryTex initialIncompleteTriangleStripGeometryTex = new InitialIncompleteTriangleStripGeometryTex();

	private final InitialIncompleteTriangleStripGeometryCol initialIncompleteTriangleStripGeometryCol = new InitialIncompleteTriangleStripGeometryCol();

	private final InitialIncompleteTriangleStripGeometryNor initialIncompleteTriangleStripGeometryNor = new InitialIncompleteTriangleStripGeometryNor();

	private final IncompleteTriangleStripGeometry incompleteTriangleStripGeometry = new IncompleteTriangleStripGeometry();

	private final IncompleteTriangleStripGeometryTex incompleteTriangleStripGeometryTex = new IncompleteTriangleStripGeometryTex();

	private final IncompleteTriangleStripGeometryCol incompleteTriangleStripGeometryCol = new IncompleteTriangleStripGeometryCol();

	private final IncompleteTriangleStripGeometryNor incompleteTriangleStripGeometryNor = new IncompleteTriangleStripGeometryNor();

	public TexturedColoredNormaledBuffer(int aNumberOfVertices) {
		super(aNumberOfVertices, MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + NORMAL_COORDINATES_PER_VERTEX + COLOUR_PARTS_PER_VERTEX);
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
		aGL10.glVertexPointer(3, GL10.GL_FIXED, DATA_ELEMENTS_PER_TRIANGLE - MODEL_COORDINATES_PER_VERTEX * VERTICES_PER_TRIANGLE, vertexDataAsBuffer);

		aGL10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		vertexDataAsBuffer.position(VERTICES_PER_TRIANGLE * MODEL_COORDINATES_PER_VERTEX);
		final IntBuffer texCoordBuffer = vertexDataAsBuffer.slice();
		aGL10.glTexCoordPointer(2, GL10.GL_FIXED, DATA_ELEMENTS_PER_TRIANGLE - TEXTURE_COORDINATES_PER_VERTEX * VERTICES_PER_TRIANGLE, texCoordBuffer);

		aGL10.glEnableClientState(GL10.GL_COLOR_ARRAY);
		vertexDataAsBuffer.position(VERTICES_PER_TRIANGLE * (MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX));
		final IntBuffer colorBuffer = vertexDataAsBuffer.slice();
		aGL10.glColorPointer(COLOUR_PARTS_PER_VERTEX, GL10.GL_FIXED, DATA_ELEMENTS_PER_TRIANGLE - COLOUR_PARTS_PER_VERTEX * VERTICES_PER_TRIANGLE, colorBuffer);

		aGL10.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		vertexDataAsBuffer.position(VERTICES_PER_TRIANGLE * (MODEL_COORDINATES_PER_VERTEX + TEXTURE_COORDINATES_PER_VERTEX + COLOUR_PARTS_PER_VERTEX));
		final IntBuffer normalBuffer = vertexDataAsBuffer.slice();
		aGL10.glNormalPointer(GL10.GL_FIXED, DATA_ELEMENTS_PER_TRIANGLE - NORMAL_COORDINATES_PER_VERTEX * VERTICES_PER_TRIANGLE, normalBuffer);
	}

	public void disable(GL10 aGL10) {
		aGL10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		aGL10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		aGL10.glDisableClientState(GL10.GL_COLOR_ARRAY);
		aGL10.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
}
