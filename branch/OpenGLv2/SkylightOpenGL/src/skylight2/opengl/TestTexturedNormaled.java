package skylight2.opengl;

import org.junit.Test;

import skylight2.opengl.TexturedNormaledGeometryGroup.IncompleteTriangleStripGeometry;
import skylight2.opengl.TexturedNormaledGeometryGroup.IncompleteTrianglesGeometry;

public class TestTexturedNormaled {
	@Test
	public void testTexturedNormaledGeometryGroup() {
		TexturedNormaledGeometryGroup geometryGroup = new TexturedNormaledGeometryGroup(9 + 5);
		Geometry trianglesGeometry =
				geometryGroup.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipNormals().endGeometry();
		Geometry triangleStripGeometry =
				geometryGroup.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0)
						.setTextures(0, 0).setNormals(0, 0, 0).addTriangle(0, 0, 0).skipTextures().skipNormals()
						.endGeometry();
	}

	@Test
	public void testNestedTexturedNormaledGeometryGroup() {
		TexturedNormaledGeometryGroup geometryGroup = new TexturedNormaledGeometryGroup(9 + 5);
		final IncompleteTrianglesGeometry trianglePart =
				geometryGroup.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0);
		Geometry nestedTrianglesGeometry =
				trianglePart.startNestedGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipNormals().endGeometry();
		Geometry outerTrianglesGeometry =
				trianglePart.addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipNormals().endGeometry();

		final IncompleteTriangleStripGeometry triangleStripPart =
				geometryGroup.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0);
		Geometry nestedTriangleStripGeometry =
				triangleStripPart.startNestedGeometry().addTriangle(0, 0, 0).setTextures(0, 0).setNormals(0, 0, 0)
						.addTriangle(0, 0, 0).skipTextures().skipNormals().endGeometry();
		Geometry triangleStripGeometry =
				triangleStripPart.addTriangle(0, 0, 0).setTextures(0, 0).setNormals(0, 0, 0).addTriangle(0, 0, 0)
						.skipTextures().skipNormals().endGeometry();
	}
}
