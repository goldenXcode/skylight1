package skylight2.opengl;

public class TexturedColoredNormaledGeometryGroup {
	public class IncompleteTrianglesGeometry {
		public IncompleteTrianglesGeometryTex addTriangle(int anX1, int aY1, int aZ1, int anX2, int aY2, int aZ2,
				int anX3, int aY3, int aZ3) {
			return null;
		}

		public Geometry endGeometry() {
			return null;
		}
	}

	public class IncompleteTrianglesGeometryTex {
		public IncompleteTrianglesGeometryCol setTextures(int aU1, int aV1, int aU2, int aV2, int aU3, int aV3) {
			return null;
		}

		public IncompleteTrianglesGeometryCol skipTextures() {
			return null;
		}
	}

	public class IncompleteTrianglesGeometryCol {
		public IncompleteTrianglesGeometryNor setColors(float aRed1, float aGreen1, float aBlue1, float aRed2,
				float aGreen2, float aBlue2, float aRed3, float aGreen3, float aBlue3) {
			return null;
		}

		public IncompleteTrianglesGeometryNor skipColors() {
			return null;
		}
	}

	public class IncompleteTrianglesGeometryNor {
		public IncompleteTrianglesGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1,
				float aNormalX2, float aNormalY2, float aNormalZ2, float aNormalX3, float aNormalY3, float aNormalZ3) {
			return null;
		}

		public IncompleteTrianglesGeometry skipNormals() {
			return null;
		}
	}

	public class InitialIncompleteTriangleStripGeometry {
		public InitialIncompleteTriangleStripGeometryTex addTriangle(int anX1, int aY1, int aZ1, int anX2, int aY2,
				int aZ2, int anX3, int aY3, int aZ3) {
			return null;
		}
	}

	public class InitialIncompleteTriangleStripGeometryTex {
		public InitialIncompleteTriangleStripGeometryCol setTextures(int aU1, int aV1, int aU2, int aV2, int aU3,
				int aV3) {
			return null;
		}

		public InitialIncompleteTriangleStripGeometryCol skipTextures() {
			return null;
		}
	}

	public class InitialIncompleteTriangleStripGeometryCol {
		public InitialIncompleteTriangleStripGeometryNor setColors(float aRed1, float aGreen1, float aBlue1,
				float aRed2, float aGreen2, float aBlue2, float aRed3, float aGreen3, float aBlue3) {
			return null;
		}

		public InitialIncompleteTriangleStripGeometryNor skipColors() {
			return null;
		}
	}

	public class InitialIncompleteTriangleStripGeometryNor {
		public IncompleteTriangleStripGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1,
				float aNormalX2, float aNormalY2, float aNormalZ2, float aNormalX3, float aNormalY3, float aNormalZ3) {
			return null;
		}

		public IncompleteTriangleStripGeometry skipNormals() {
			return null;
		}
	}

	public class IncompleteTriangleStripGeometry {
		public IncompleteTriangleStripGeometryTex addTriangle(int anX1, int aY1, int aZ1) {
			return null;
		}

		public Geometry endGeometry() {
			return null;
		}
	}

	public class IncompleteTriangleStripGeometryTex {
		public IncompleteTriangleStripGeometryCol setTextures(int aU1, int aV1) {
			return null;
		}

		public IncompleteTriangleStripGeometryCol skipTextures() {
			return null;
		}
	}

	public class IncompleteTriangleStripGeometryCol {
		public IncompleteTriangleStripGeometryNor setColors(float aRed1, float aGreen1, float aBlue1) {
			return null;
		}

		public IncompleteTriangleStripGeometryNor skipColors() {
			return null;
		}
	}

	public class IncompleteTriangleStripGeometryNor {
		public IncompleteTriangleStripGeometry setNormals(float aNormalX1, float aNormalY1, float aNormalZ1) {
			return null;
		}

		public IncompleteTriangleStripGeometry skipNormals() {
			return null;
		}
	}

	public IncompleteTrianglesGeometry startTrianglesGeometry() {
		return new IncompleteTrianglesGeometry();
	}

	public InitialIncompleteTriangleStripGeometry startTriangleStripGeometry() {
		return new InitialIncompleteTriangleStripGeometry();
	}
}
