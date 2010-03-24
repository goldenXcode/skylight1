package skylight2.opengl;

public class Geometry {
	final GeometryGroup geometryGroup;

	final int vertexDataStartOffset;

	final int renderingMode;

	int vertexDataLength;

	public Geometry(final GeometryGroup aGeometryGroup, final int aVertexDataStartOffset, final int aRenderingMode) {
		geometryGroup = aGeometryGroup;
		vertexDataStartOffset = aVertexDataStartOffset;
		renderingMode = aRenderingMode;
	}
}
