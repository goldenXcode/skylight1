package skylight2.opengl;

import javax.microedition.khronos.opengles.GL10;

import skylight2.opengl.ModelBuffer.IncompleteTriangleStripGeometry;
import skylight2.opengl.ModelBuffer.IncompleteTrianglesGeometry;

public class TestVertex {
	private GL10 gL10 = new DummyGL10();

	public void testVertexGeometryGroup() {
		ModelBuffer buffer = new ModelBuffer(9 + 5);
		Geometry trianglesGeometry =
				buffer.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0)
						.addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).endGeometry();
		trianglesGeometry.draw(gL10);
		Geometry triangleStripGeometry =
				buffer.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0).addTriangle(0, 0, 0).endGeometry();
		triangleStripGeometry.draw(gL10);
	}

	public void testNestedVertexGeometryGroup() {
		ModelBuffer buffer = new ModelBuffer(9 + 5);
		final IncompleteTrianglesGeometry trianglePart = buffer.startTrianglesGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0);
		Geometry nestedTrianglesGeometry =
				trianglePart.startNestedGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).endGeometry();
		Geometry outerTrianglesGeometry = trianglePart.addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0).endGeometry();
		nestedTrianglesGeometry.draw(gL10);
		outerTrianglesGeometry.draw(gL10);

		final IncompleteTriangleStripGeometry triangleStripPart = buffer.startTriangleStripGeometry().addTriangle(0, 0, 0, 0, 0, 0, 0, 0, 0);
		Geometry nestedTriangleStripGeometry = triangleStripPart.startNestedGeometry().addTriangle(0, 0, 0).addTriangle(0, 0, 0).endGeometry();
		Geometry triangleStripGeometry = triangleStripPart.addTriangle(0, 0, 0).addTriangle(0, 0, 0).endGeometry();
		nestedTriangleStripGeometry.draw(gL10);
		triangleStripGeometry.draw(gL10);
	}
}
