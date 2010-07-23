package skylight2.opengl;

import javax.microedition.khronos.opengles.GL10;

import skylight2.opengl.TexturedNormaledBuffer.IncompleteTriangleStripGeometry;
import skylight2.opengl.TexturedNormaledBuffer.IncompleteTrianglesGeometry;

public class TestTexturedNormaled {
	private GL10 gL10 = new DummyGL10();

	public void testTexturedNormaledGeometryGroup() {
		TexturedNormaledBuffer buffer = new TexturedNormaledBuffer(9 + 5);
		Geometry trianglesGeometry =
				buffer.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipNormals().endGeometry();
		trianglesGeometry.draw(gL10);
		Geometry triangleStripGeometry =
				buffer.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0)
						.setTextures(0, 0).setNormals(0, 0, 0).addTriangle(0, 0, 0).skipTextures().skipNormals()
						.endGeometry();
		triangleStripGeometry.draw(gL10);
	}

	public void testNestedTexturedNormaledGeometryGroup() {
		TexturedNormaledBuffer buffer = new TexturedNormaledBuffer(9 + 5);
		final IncompleteTrianglesGeometry trianglePart =
				buffer.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0);
		Geometry nestedTrianglesGeometry =
				trianglePart.startNestedGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipNormals().endGeometry();
		Geometry outerTrianglesGeometry =
				trianglePart.addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).setTextures(0, 0, 0, 0, 0, 0)
						.setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).skipTextures()
						.skipNormals().endGeometry();
		nestedTrianglesGeometry.draw(gL10);
		outerTrianglesGeometry.draw(gL10);

		final IncompleteTriangleStripGeometry triangleStripPart =
				buffer.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.setTextures(0, 0, 0, 0, 0, 0).setNormals(0, 0, 0, 0, 0, 0, 0, 0, 0);
		Geometry nestedTriangleStripGeometry =
				triangleStripPart.startNestedGeometry().addTriangle(0, 0, 0).setTextures(0, 0).setNormals(0, 0, 0)
						.addTriangle(0, 0, 0).skipTextures().skipNormals().endGeometry();
		Geometry triangleStripGeometry =
				triangleStripPart.addTriangle(0, 0, 0).setTextures(0, 0).setNormals(0, 0, 0).addTriangle(0, 0, 0)
						.skipTextures().skipNormals().endGeometry();
		nestedTriangleStripGeometry.draw(gL10);
		triangleStripGeometry.draw(gL10);
	}
}
