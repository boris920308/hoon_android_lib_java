package hoon.example.hoon_hw_stress;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HoonGLRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private FloatBuffer vertexBuffer;
    private final int COORDS_PER_VERTEX = 3;
    private final float[] triangleCoords = {
            0.0f,  0.622008459f, 0.0f,
            -0.5f, -0.311004243f, 0.0f,
            0.5f, -0.311004243f, 0.0f
    };
    private final String vertexShaderCode;
    private final String fragmentShaderCode;
    private int mProgram;
    private int positionHandle;
    private boolean isLoading = false;

    public HoonGLRenderer(Context context) {
        this.context = context;
        vertexShaderCode = loadShaderFromAssets("vertex_shader.glsl");
        fragmentShaderCode = loadShaderFromAssets("fragment_shader.glsl");

        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);
    }

    private String loadShaderFromAssets(String filename) {
        StringBuilder shaderCode = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(filename);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            shaderCode.append(new String(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shaderCode.toString();
    }

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(mProgram);

        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(positionHandle);

        if (isLoading) {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, triangleCoords.length / COORDS_PER_VERTEX);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void startLoad() {
        isLoading = true;
    }

    public void stopLoad() {
        isLoading = false;
    }
}
