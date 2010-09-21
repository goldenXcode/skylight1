/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.jsr239.wtksamples.cube;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import skylight2.opengl.Geometry;
import skylight2.opengl.ModelBuffer;
import skylight2.opengl.TexturedNormaledBuffer;
import skylight2.opengl.TexturedNormaledBuffer.IncompleteTrianglesGeometry;
import skylight2.opengl.files.ObjFileLoader;
import skylight2.opengl.Texture;

class CubeCanvas extends GameCanvas implements Runnable {

    boolean initialized = false;
    int frame = 0;
    float time = 0.0f;
    Graphics g;
    int width;
    int height;
    Cube cube;
    EGL10 egl;
    GL10 gl;
    EGLConfig eglConfig;
    EGLDisplay eglDisplay;
    EGLSurface eglWindowSurface;
    EGLContext eglContext;
    ModelBuffer geometryBuffer;
    Geometry geometry;
    TexturedNormaledBuffer buffer;
    Geometry sphinx;
    Geometry ground;
    Image sphinxTextureImage;
    Image atlasTextureImage;
    int[] sphinxRGBData;
    int[] atlasRGBData;
    Texture sphinxTexture;
    Texture atlasTexture;
    float direction = 0;
    float positionX = 30;
    float positionZ = 50;
    float speed = 0;
    private static final float HEIGHT_OF_PLAYER = 35;

    public CubeCanvas(Cube cube) {
        super(true);
        this.cube = cube;
        this.g = this.getGraphics();

        this.width = getWidth();
        this.height = getHeight();

        buffer = new TexturedNormaledBuffer(924 + (20 * 20 * 2 * 3));
        try {
            InputStream is = CubeCanvas.class.getResourceAsStream("sphinx_scaled.obj");
            ObjFileLoader objFileLoader = new ObjFileLoader(is);
            sphinx = objFileLoader.createGeometry(buffer);
            ground = makeGround(buffer);
            buffer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private int getProperty(String propName, int def) {
        String s = cube.getAppProperty(propName);
        int val = (s == null) ? def : Integer.parseInt(s);

        return val;
    }

    public void init() {
        this.egl = (EGL10) EGLContext.getEGL();

        this.eglDisplay = egl.eglGetDisplay(egl.EGL_DEFAULT_DISPLAY);

        int[] major_minor = new int[2];
        egl.eglInitialize(eglDisplay, major_minor);

        int[] num_config = new int[1];

        egl.eglGetConfigs(eglDisplay, null, 0, num_config);
        System.out.println("num_config[0] = " + num_config[0]);

        int redSize = getProperty("jsr239.redSize", 8);
        int greenSize = getProperty("jsr239.greenSize", 8);
        int blueSize = getProperty("jsr239.blueSize", 8);
        int alphaSize = getProperty("jsr239.alphaSize", 0);
        int depthSize = getProperty("jsr239.depthSize", 32);
        int stencilSize = getProperty("jsr239.stencilSize", EGL10.EGL_DONT_CARE);

        int[] s_configAttribs = {
            EGL10.EGL_RED_SIZE, redSize, EGL10.EGL_GREEN_SIZE, greenSize, EGL10.EGL_BLUE_SIZE,
            blueSize, EGL10.EGL_ALPHA_SIZE, alphaSize, EGL10.EGL_DEPTH_SIZE, depthSize,
            EGL10.EGL_STENCIL_SIZE, stencilSize, EGL10.EGL_NONE
        };

        EGLConfig[] eglConfigs = new EGLConfig[num_config[0]];
        egl.eglChooseConfig(eglDisplay, s_configAttribs, eglConfigs, eglConfigs.length, num_config);
        System.out.println("num_config[0] = " + num_config[0]);

        this.eglConfig = eglConfigs[0];

        this.eglContext = egl.eglCreateContext(eglDisplay, eglConfig, EGL10.EGL_NO_CONTEXT, null);

        this.gl = (GL10) eglContext.getGL();

        this.eglWindowSurface = egl.eglCreateWindowSurface(eglDisplay, eglConfig, g, null);

        this.initialized = true;
    }

    private void generateTextures() {
        if (sphinxTexture != null) {
            sphinxTexture.activateTexture();
            atlasTexture.activateTexture();
        } else {
            final InputStream atlasTextureInputStream = CubeCanvas.class.getResourceAsStream("textures.PNG");
            try {
                atlasTextureImage = Image.createImage(atlasTextureInputStream);
                atlasRGBData = new int[atlasTextureImage.getWidth() * atlasTextureImage.getHeight()];
                atlasTextureImage.getRGB(atlasRGBData, 0, atlasTextureImage.getWidth(), 0, 0, atlasTextureImage.getWidth(), atlasTextureImage.getHeight());
                Texture.swapByteOrder(atlasRGBData);
                atlasTexture = new Texture(gl, atlasRGBData, atlasTextureImage.getWidth(), atlasTextureImage.getHeight());
                atlasTextureImage = null;
                atlasRGBData = null;
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            final InputStream sphinxTextureInputStream = CubeCanvas.class.getResourceAsStream("sphinx.png");
            try {
                sphinxTextureImage = Image.createImage(sphinxTextureInputStream);
                sphinxRGBData = new int[sphinxTextureImage.getWidth() * sphinxTextureImage.getHeight()];
                sphinxTextureImage.getRGB(sphinxRGBData, 0, sphinxTextureImage.getWidth(), 0, 0, sphinxTextureImage.getWidth(), sphinxTextureImage.getHeight());
                Texture.swapByteOrder(sphinxRGBData);
                sphinxTexture = new Texture(gl, sphinxRGBData, sphinxTextureImage.getWidth(), sphinxTextureImage.getHeight());
                sphinxTextureImage = null;
                sphinxRGBData = null;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void perspective(float fovy, float aspect, float zNear, float zFar) {
        float xmin;
        float xmax;
        float ymin;
        float ymax;

        ymax = zNear * (float) Math.tan((fovy * Math.PI) / 360.0);
        ymin = -ymax;
        xmin = ymin * aspect;
        xmax = ymax * aspect;

        gl.glFrustumf(xmin, xmax, ymin, ymax, zNear, zFar);
    }

    private void updateState(int width, int height) {
        float[] light_position = {-50.f, 50.f, 50.f, 0.f};
        float[] light_ambient = {0.5f, 0.5f, 0.5f, 1.f};
        float[] light_diffuse = {1.0f, 1.0f, 1.0f, 1.f};
//        float[] material_spec = {1.0f, 1.0f, 1.0f, 0.f};
//        float[] zero_vec4 = {0.0f, 0.0f, 0.0f, 0.f};

        float aspect = (height != 0) ? ((float) width / (float) height) : 1.0f;

        gl.glViewport(0, 0, width, height);
        gl.glScissor(0, 0, width, height);

        gl.glActiveTexture(GL10.GL_TEXTURE0);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        generateTextures();

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, light_ambient, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light_position, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, light_diffuse, 0);
//        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, zero_vec4, 0);
//        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, material_spec, 0);

        gl.glDisable(GL10.GL_BLEND);
//        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_SRC_COLOR);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_NORMALIZE);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
//        gl.glEnable(GL10.GL_COLOR_MATERIAL);
        gl.glEnable(GL10.GL_CULL_FACE);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

//        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glDisable(GL10.GL_DITHER);

        // Clear background to blue
        gl.glClearColor(0.8f, 0.7f, 1f, 1.0f);

//        gl.glEnable(GL10.GL_FOG);
//        gl.glFogf(GL10.GL_FOG_START, 100f);
//        gl.glFogf(GL10.GL_FOG_END, 150f);
//        gl.glFogf(GL10.GL_FOG_MODE, GL10.GL_LINEAR);
//        gl.glFogfv(GL10.GL_FOG_COLOR, new float[] {1f, 1f, 1f, 0.5f}, 0);


//	gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        perspective(90.f, aspect, 1f, 1000.f);

        gl.glFinish();
    }

    private void drawScene() {
        // Make the context current on this thread
        egl.eglMakeCurrent(eglDisplay, eglWindowSurface, eglWindowSurface, eglContext);

        // Perform setup and clear background using GL
        egl.eglWaitNative(EGL10.EGL_CORE_NATIVE_ENGINE, g);

        updateState(width, height);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glFinish();

        // Wait for GL to complete
        egl.eglWaitGL();

        // Draw the scene using GL
        egl.eglWaitNative(EGL10.EGL_CORE_NATIVE_ENGINE, g);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glRotatef(-direction, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(-positionX, -HEIGHT_OF_PLAYER, -positionZ);

//        gl.glColor4f(1, 1, 1, 1);
        buffer.enable(gl);
        atlasTexture.activateTexture();
        ground.draw(gl);
        sphinxTexture.activateTexture();
        sphinx.draw(gl);
        buffer.disable(gl);

        gl.glFinish();

        time += 0.1f;

        // Get the key state and store it
        int keyState = getKeyStates();
        if ((keyState & LEFT_PRESSED) != 0) {
            direction += 5;
        } else if ((keyState & RIGHT_PRESSED) != 0) {
            direction -= 5;
        }
        if ((keyState & UP_PRESSED) != 0) {
            speed += 0.1f;
        } else if ((keyState & DOWN_PRESSED) != 0) {
            speed -= 0.1f;
        }

        positionX -= Math.sin(Math.PI * direction / 180f) * speed;
        positionZ -= Math.cos(Math.PI * direction / 180f) * speed;

        egl.eglWaitGL();

        // Release the context
        egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
                EGL10.EGL_NO_CONTEXT);
    }

    public void shutdown() {
        sphinxTexture.freeTexture();
        egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
                EGL10.EGL_NO_CONTEXT);
        egl.eglDestroyContext(eglDisplay, eglContext);
        egl.eglDestroySurface(eglDisplay, eglWindowSurface);
        egl.eglTerminate(eglDisplay);
    }

    public void run() {
        if (!initialized) {
            init();
        }

        try {
            while (!cube.isFinished()) {
                if (!cube.paused) {
                    Thread.sleep(2);
                    drawScene();
                    flushGraphics();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        shutdown();
    }

    private Geometry makeGround(TexturedNormaledBuffer aBuffer) {
        IncompleteTrianglesGeometry incompleteGeometry = aBuffer.startTrianglesGeometry();

        float u1 = 320f / 1024f;
        float u2 = (320f + 256f) / 1024f;
        float v1 = 0;
        float v2 = 256f / 1024f;
        final float GROUND_EDGE_LENGTH = 50;

        for (int x = -10; x < 10; x++) {
            for (int z = -10; z < 10; z++) {
                float h00 = new Random(x+z*100).nextFloat() * 20f - 10f;
                float h10 = new Random(x+1+z*100).nextFloat() * 20f - 10f;
                float h01 = new Random(x+(z+1)*100).nextFloat() * 20f - 10f;
                float h11 = new Random(x+1+(z+1)*100).nextFloat() * 20f - 10f;
                incompleteGeometry = incompleteGeometry.addTriangle(x * GROUND_EDGE_LENGTH, h00, z * GROUND_EDGE_LENGTH, x * GROUND_EDGE_LENGTH, h01, (z + 1f) * GROUND_EDGE_LENGTH, (x + 1f) * GROUND_EDGE_LENGTH, h10, z * GROUND_EDGE_LENGTH).
                        setTextures(u1, v1, u1, v2, u2, v1).
                        setNormals(0, 1, 0, 0, 1, 0, 0, 1, 0);
                incompleteGeometry = incompleteGeometry.addTriangle(x * GROUND_EDGE_LENGTH, h01, (z + 1f) * GROUND_EDGE_LENGTH, (x + 1f) * GROUND_EDGE_LENGTH, h11, (z + 1f) * GROUND_EDGE_LENGTH, (x + 1f) * GROUND_EDGE_LENGTH, h10, z * GROUND_EDGE_LENGTH).
                        setTextures(u1, v2, u2, v2, u2, v1).
                        setNormals(0, 1, 0, 0, 1, 0, 0, 1, 0);
            }
        }
        return incompleteGeometry.endGeometry();
    }
}
