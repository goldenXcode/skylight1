package skylight2.opengl;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class DummyGL10 implements GL10 {

	public void glActiveTexture(int aArg0) {
	}

	public void glAlphaFunc(int aArg0, float aArg1) {
	}

	public void glAlphaFuncx(int aArg0, int aArg1) {
	}

	public void glBindTexture(int aArg0, int aArg1) {
	}

	public void glBlendFunc(int aArg0, int aArg1) {
	}

	public void glClear(int aArg0) {
	}

	public void glClearColor(float aArg0, float aArg1, float aArg2, float aArg3) {
	}

	public void glClearColorx(int aArg0, int aArg1, int aArg2, int aArg3) {
	}

	public void glClearDepthf(float aArg0) {
	}

	public void glClearDepthx(int aArg0) {
	}

	public void glClearStencil(int aArg0) {
	}

	public void glClientActiveTexture(int aArg0) {
	}

	public void glColor4f(float aArg0, float aArg1, float aArg2, float aArg3) {
	}

	public void glColor4x(int aArg0, int aArg1, int aArg2, int aArg3) {
	}

	public void glColorMask(boolean aArg0, boolean aArg1, boolean aArg2, boolean aArg3) {
	}

	public void glColorPointer(int aArg0, int aArg1, int aArg2, Buffer aArg3) {
	}

	public void glCompressedTexImage2D(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5, int aArg6, Buffer aArg7) {
	}

	public void glCompressedTexSubImage2D(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5, int aArg6, int aArg7, Buffer aArg8) {
	}

	public void glCopyTexImage2D(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5, int aArg6, int aArg7) {
	}

	public void glCopyTexSubImage2D(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5, int aArg6, int aArg7) {
	}

	public void glCullFace(int aArg0) {
	}

	public void glDeleteTextures(int aArg0, IntBuffer aArg1) {

	}

	public void glDeleteTextures(int aArg0, int[] aArg1, int aArg2) {

	}

	public void glDepthFunc(int aArg0) {

	}

	public void glDepthMask(boolean aArg0) {

	}

	public void glDepthRangef(float aArg0, float aArg1) {

	}

	public void glDepthRangex(int aArg0, int aArg1) {

	}

	public void glDisable(int aArg0) {

	}

	public void glDisableClientState(int aArg0) {

	}

	public void glDrawArrays(int aArg0, int aArg1, int aArg2) {

	}

	public void glDrawElements(int aArg0, int aArg1, int aArg2, Buffer aArg3) {

	}

	public void glEnable(int aArg0) {

	}

	public void glEnableClientState(int aArg0) {

	}

	public void glFinish() {

	}

	public void glFlush() {

	}

	public void glFogf(int aArg0, float aArg1) {

	}

	public void glFogfv(int aArg0, FloatBuffer aArg1) {

	}

	public void glFogfv(int aArg0, float[] aArg1, int aArg2) {

	}

	public void glFogx(int aArg0, int aArg1) {

	}

	public void glFogxv(int aArg0, IntBuffer aArg1) {

	}

	public void glFogxv(int aArg0, int[] aArg1, int aArg2) {

	}

	public void glFrontFace(int aArg0) {

	}

	public void glFrustumf(float aArg0, float aArg1, float aArg2, float aArg3, float aArg4, float aArg5) {

	}

	public void glFrustumx(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5) {

	}

	public void glGenTextures(int aArg0, IntBuffer aArg1) {

	}

	public void glGenTextures(int aArg0, int[] aArg1, int aArg2) {

	}

	public int glGetError() {

		return 0;
	}

	public void glGetIntegerv(int aArg0, IntBuffer aArg1) {

	}

	public void glGetIntegerv(int aArg0, int[] aArg1, int aArg2) {

	}

	public String glGetString(int aArg0) {

		return null;
	}

	public void glHint(int aArg0, int aArg1) {

	}

	public void glLightModelf(int aArg0, float aArg1) {

	}

	public void glLightModelfv(int aArg0, FloatBuffer aArg1) {

	}

	public void glLightModelfv(int aArg0, float[] aArg1, int aArg2) {

	}

	public void glLightModelx(int aArg0, int aArg1) {

	}

	public void glLightModelxv(int aArg0, IntBuffer aArg1) {

	}

	public void glLightModelxv(int aArg0, int[] aArg1, int aArg2) {

	}

	public void glLightf(int aArg0, int aArg1, float aArg2) {

	}

	public void glLightfv(int aArg0, int aArg1, FloatBuffer aArg2) {

	}

	public void glLightfv(int aArg0, int aArg1, float[] aArg2, int aArg3) {

	}

	public void glLightx(int aArg0, int aArg1, int aArg2) {

	}

	public void glLightxv(int aArg0, int aArg1, IntBuffer aArg2) {

	}

	public void glLightxv(int aArg0, int aArg1, int[] aArg2, int aArg3) {

	}

	public void glLineWidth(float aArg0) {

	}

	public void glLineWidthx(int aArg0) {

	}

	public void glLoadIdentity() {

	}

	public void glLoadMatrixf(FloatBuffer aArg0) {

	}

	public void glLoadMatrixf(float[] aArg0, int aArg1) {

	}

	public void glLoadMatrixx(IntBuffer aArg0) {

	}

	public void glLoadMatrixx(int[] aArg0, int aArg1) {

	}

	public void glLogicOp(int aArg0) {

	}

	public void glMaterialf(int aArg0, int aArg1, float aArg2) {

	}

	public void glMaterialfv(int aArg0, int aArg1, FloatBuffer aArg2) {

	}

	public void glMaterialfv(int aArg0, int aArg1, float[] aArg2, int aArg3) {

	}

	public void glMaterialx(int aArg0, int aArg1, int aArg2) {

	}

	public void glMaterialxv(int aArg0, int aArg1, IntBuffer aArg2) {

	}

	public void glMaterialxv(int aArg0, int aArg1, int[] aArg2, int aArg3) {

	}

	public void glMatrixMode(int aArg0) {

	}

	public void glMultMatrixf(FloatBuffer aArg0) {

	}

	public void glMultMatrixf(float[] aArg0, int aArg1) {

	}

	public void glMultMatrixx(IntBuffer aArg0) {

	}

	public void glMultMatrixx(int[] aArg0, int aArg1) {

	}

	public void glMultiTexCoord4f(int aArg0, float aArg1, float aArg2, float aArg3, float aArg4) {

	}

	public void glMultiTexCoord4x(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4) {

	}

	public void glNormal3f(float aArg0, float aArg1, float aArg2) {

	}

	public void glNormal3x(int aArg0, int aArg1, int aArg2) {

	}

	public void glNormalPointer(int aArg0, int aArg1, Buffer aArg2) {

	}

	public void glOrthof(float aArg0, float aArg1, float aArg2, float aArg3, float aArg4, float aArg5) {

	}

	public void glOrthox(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5) {

	}

	public void glPixelStorei(int aArg0, int aArg1) {

	}

	public void glPointSize(float aArg0) {

	}

	public void glPointSizex(int aArg0) {

	}

	public void glPolygonOffset(float aArg0, float aArg1) {

	}

	public void glPolygonOffsetx(int aArg0, int aArg1) {

	}

	public void glPopMatrix() {

	}

	public void glPushMatrix() {

	}

	public void glReadPixels(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5, Buffer aArg6) {

	}

	public void glRotatef(float aArg0, float aArg1, float aArg2, float aArg3) {

	}

	public void glRotatex(int aArg0, int aArg1, int aArg2, int aArg3) {

	}

	public void glSampleCoverage(float aArg0, boolean aArg1) {

	}

	public void glSampleCoveragex(int aArg0, boolean aArg1) {

	}

	public void glScalef(float aArg0, float aArg1, float aArg2) {

	}

	public void glScalex(int aArg0, int aArg1, int aArg2) {

	}

	public void glScissor(int aArg0, int aArg1, int aArg2, int aArg3) {

	}

	public void glShadeModel(int aArg0) {

	}

	public void glStencilFunc(int aArg0, int aArg1, int aArg2) {

	}

	public void glStencilMask(int aArg0) {

	}

	public void glStencilOp(int aArg0, int aArg1, int aArg2) {

	}

	public void glTexCoordPointer(int aArg0, int aArg1, int aArg2, Buffer aArg3) {

	}

	public void glTexEnvf(int aArg0, int aArg1, float aArg2) {

	}

	public void glTexEnvfv(int aArg0, int aArg1, FloatBuffer aArg2) {

	}

	public void glTexEnvfv(int aArg0, int aArg1, float[] aArg2, int aArg3) {

	}

	public void glTexEnvx(int aArg0, int aArg1, int aArg2) {

	}

	public void glTexEnvxv(int aArg0, int aArg1, IntBuffer aArg2) {

	}

	public void glTexEnvxv(int aArg0, int aArg1, int[] aArg2, int aArg3) {

	}

	public void glTexImage2D(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5, int aArg6, int aArg7, Buffer aArg8) {

	}

	public void glTexParameterf(int aArg0, int aArg1, float aArg2) {

	}

	public void glTexParameterx(int aArg0, int aArg1, int aArg2) {

	}

	public void glTexSubImage2D(int aArg0, int aArg1, int aArg2, int aArg3, int aArg4, int aArg5, int aArg6, int aArg7, Buffer aArg8) {

	}

	public void glTranslatef(float aArg0, float aArg1, float aArg2) {

	}

	public void glTranslatex(int aArg0, int aArg1, int aArg2) {

	}

	public void glVertexPointer(int aArg0, int aArg1, int aArg2, Buffer aArg3) {

	}

	public void glViewport(int aArg0, int aArg1, int aArg2, int aArg3) {

	}

}
