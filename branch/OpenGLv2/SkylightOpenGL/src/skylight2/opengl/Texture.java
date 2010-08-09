package skylight2.opengl;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Encapsulates an OpenGL texture, with facilities for loading, activating, deactivating, and freeing. Warning: if mip
 * maps are used, which is the default, extra texture space is consumed and additional textures may result in a major
 * impact to rendering performance.
 */
public class Texture {
	private int textureId;

	private GL10 gL10;

	private boolean textureAllocated;

	/**
	 * Load a texture from an input stream.
	 */
	public Texture(final GL10 aGL10, final int[] anRGBData, final int aWidth, final int aHeight) {
		// save for later
		gL10 = aGL10;

		// generate a texture
		final int[] textures = new int[1];
		aGL10.glGenTextures(textures.length, textures, 0);
		textureId = textures[0];
		textureAllocated = true;

		// bind and activate the texture
		activateTexture();

		int results[] = new int[2];
		aGL10.glGetIntegerv(GL10.GL_MAX_TEXTURE_UNITS, results, 0);
		aGL10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, results, 1);
		System.out.println("available texture units " + results[0] + ", max side " + results[1]);

		// TODO does it make sense to set these this early?
		// TODO does this need to be decided by the client?
		// TODO does this belong to the texture or the geometry?
		aGL10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		aGL10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		aGL10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		aGL10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		// copy to a direct NIO buffer
		ByteBuffer textureBuffer = ByteBuffer.allocateDirect(4 * anRGBData.length);
		textureBuffer.asIntBuffer().put(anRGBData);
		textureBuffer.position(0);
		gL10.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, aWidth, aHeight, 0, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, textureBuffer);
	}

	// swap the byte ordering from MIDP to OpenGL ES
	public static void swapByteOrder(final int[] anRGBData) {
		for (int i = 0; i < anRGBData.length; i++) {
			int j = anRGBData[i];
			anRGBData[i] = (j & 0xff000000) | ((j & 0x00ff0000) >> 16) | (j & 0x0000ff00) | ((j & 0x000000ff) << 16);
		}
	}

	/**
	 * Activate this texture. This is <b>not</b> required if the texture was the last texture loaded, as loading a
	 * texture automatically activates it.
	 */
	public void activateTexture() {
		gL10.glEnable(GL10.GL_TEXTURE_2D);
		gL10.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	}

	/**
	 * Deactivate this texture. Only required if the next rendering requires that no texture be active.
	 */
	public void deactivateTexture() {
		// TODO is this method really needed!!!? OpenGL seems to push out the LRU texture anyway
		gL10.glDisable(GL10.GL_TEXTURE_2D);
	}

	/**
	 * Free this texture. Only required if the texture must be deleted from OpenGL sooner than this object being GC'd.
	 */
	public void freeTexture() {
		// release the texture
		if (textureAllocated) {
			try {
				gL10.glDeleteTextures(1, new int[] { textureId }, 0);
			} catch (Exception e) {
				// if the texture is no longer extant and the GLWrapper is in place, then a harmless exception is thrown
			}
		}
		textureAllocated = false;
	}

	public int getTextureId() {
		return textureId;
	}
}
