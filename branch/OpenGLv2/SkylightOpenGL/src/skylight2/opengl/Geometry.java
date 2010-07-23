package skylight2.opengl;

import javax.microedition.khronos.opengles.GL10;

public class Geometry {
	final Buffer buffer;

	final int vertexDataStartOffset;

	final int renderingMode;

	int vertexDataLength;

	public Geometry(final Buffer aBuffer, final int aVertexDataStartOffset, final int aRenderingMode) {
		buffer = aBuffer;
		vertexDataStartOffset = aVertexDataStartOffset;
		renderingMode = aRenderingMode;
	}

	public void draw(GL10 aGL10) {
		aGL10.glDrawArrays(renderingMode, vertexDataStartOffset, vertexDataLength);
	}

	int getVertexDataStartOffset() {
		return vertexDataStartOffset;
	}
	
	void setVertexDataLength(int aVertexDataLength) {
		vertexDataLength = aVertexDataLength;
	}
}
