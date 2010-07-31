package skylight2.opengl;


public class TestTexturedColoredNormaled {
	public void testTexturedColoredNormaledGeometryGroup() {
		TexturedColoredNormaledBuffer buffer = new TexturedColoredNormaledBuffer(6);
		Geometry trianglesGeometry =
				buffer.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipColors().skipNormals().endGeometry();
		Geometry triangleStripGeometry =
				buffer.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0).setTextures(0, 0)
						.setColors(0, 0, 0).setNormals(0, 0, 0).addTriangle(0, 0, 0).skipTextures().skipColors()
						.skipNormals().endGeometry();
	}

	public void testNestedTexturedColoredNormaledGeometryGroup() {
		// TODO
	}
}
