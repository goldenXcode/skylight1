package skylight2.opengl;

import org.junit.Test;

public class TestTexturedColoredNormaled {
	@Test
	public void testTexturedColoredNormaledGeometryGroup() {
		TexturedColoredNormaledGeometryGroup geometryGroup = new TexturedColoredNormaledGeometryGroup();
		Geometry trianglesGeometry =
				geometryGroup.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipColors().skipNormals().endGeometry();
		Geometry triangleStripGeometry =
				geometryGroup.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0).setTextures(0, 0)
						.setColors(0, 0, 0).setNormals(0, 0, 0).addTriangle(0, 0, 0).skipTextures().skipColors()
						.skipNormals().endGeometry();
	}

	@Test
	public void testNestedTexturedColoredNormaledGeometryGroup() {
		// TODO
	}
}
