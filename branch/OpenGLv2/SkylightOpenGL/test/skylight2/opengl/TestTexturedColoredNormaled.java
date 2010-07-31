package skylight2.opengl;

import javax.microedition.khronos.opengles.GL10;

import skylight2.opengl.TexturedColoredNormaledBuffer.IncompleteTriangleStripGeometry;

public class TestTexturedColoredNormaled {
	private GL10 gL10 = new DummyGL10();

	public void testTexturedColoredNormaledGeometryGroup() {
		TexturedColoredNormaledBuffer buffer = new TexturedColoredNormaledBuffer(6);
		Geometry trianglesGeometry =
				buffer.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0)
						.setColors(0, 0, 0, 0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipColors().skipNormals().endGeometry();
		trianglesGeometry.draw(gL10);

		Geometry triangleStripGeometry =
				buffer.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0).setTextures(0, 0).setColors(0, 0, 0).setNormals(0, 0, 0)
						.addTriangle(0, 0, 0).skipTextures().skipColors().skipNormals().endGeometry();
		triangleStripGeometry.draw(gL10);
	}

	public void testNestedTexturedColoredNormaledGeometryGroup() {
		TexturedColoredNormaledBuffer buffer = new TexturedColoredNormaledBuffer(7 + 5);
		final skylight2.opengl.TexturedColoredNormaledBuffer.IncompleteTrianglesGeometry trianglePart =
				buffer.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0);
		Geometry nestedTrianglesGeometry =
				trianglePart.startNestedGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures().skipColors().skipNormals().endGeometry();
		Geometry outerTrianglesGeometry =
				trianglePart.addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures().skipColors().skipNormals().endGeometry();
		nestedTrianglesGeometry.draw(gL10);
		outerTrianglesGeometry.draw(gL10);

		final IncompleteTriangleStripGeometry triangleStripPart =
				buffer.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0).setColors(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0);
		Geometry nestedTriangleStripGeometry =
				triangleStripPart.startNestedGeometry().addTriangle(0, 0, 0).setTextures(0, 0).setColors(0, 0, 0).setNormals(0, 0, 0).addTriangle(0, 0, 0)
						.skipTextures().skipColors().skipNormals().endGeometry();
		Geometry triangleStripGeometry =
				triangleStripPart.addTriangle(0, 0, 0).setTextures(0, 0).setColors(0, 0, 0).setNormals(0, 0, 0).addTriangle(0, 0, 0).skipTextures()
						.skipColors().skipNormals().endGeometry();
		nestedTriangleStripGeometry.draw(gL10);
		triangleStripGeometry.draw(gL10);
	}
}
