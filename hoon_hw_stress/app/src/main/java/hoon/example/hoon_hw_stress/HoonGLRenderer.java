package hoon.example.hoon_hw_stress;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HoonGLRenderer implements GLSurfaceView.Renderer {


    private int shaderProgram;
    private int positionHandle;
    private final Context mContext;

    private FloatBuffer vertexBuffer;
    private static final int vertexCount = 3000; // Example: 3000 vertices
    private static final int COORDS_PER_VERTEX = 3;
    private static final float[] vertexData = new float[vertexCount * COORDS_PER_VERTEX]; // Initialize with vertex data

    public HoonGLRenderer(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // GLSL 셰이더 코드 (블러 효과)
        String vertexShaderCode = loadShaderFromRawResource(R.raw.vertex_shader);
        String fragmentShaderCode = loadShaderFromRawResource(R.raw.fragment_shader);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);

        // Initialize vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(vertexData.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexData);
        vertexBuffer.position(0);

        GLES20.glUseProgram(shaderProgram);
        positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(shaderProgram);
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, COORDS_PER_VERTEX * 4, vertexBuffer);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount / COORDS_PER_VERTEX);
        GLES20.glDisableVertexAttribArray(positionHandle);

        // 렌더링 코드 (블러 처리 등 후처리 적용)
        renderQuad();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    // 리소스에서 셰이더 파일을 로드하는 메소드
    private String loadShaderFromRawResource(int resId) {
        InputStream inputStream = mContext.getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder shaderCode = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                shaderCode.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shaderCode.toString();
    }

    private void renderQuad() {
        // For example, create a lot of triangles
        for (int i = 0; i < vertexCount; i += 9) {
            // Define a triangle
            vertexData[i] = (float) Math.random() * 2 - 1;
            vertexData[i + 1] = (float) Math.random() * 2 - 1;
            vertexData[i + 2] = 0;

            vertexData[i + 3] = (float) Math.random() * 2 - 1;
            vertexData[i + 4] = (float) Math.random() * 2 - 1;
            vertexData[i + 5] = 0;

            vertexData[i + 6] = (float) Math.random() * 2 - 1;
            vertexData[i + 7] = (float) Math.random() * 2 - 1;
            vertexData[i + 8] = 0;
        }

        // Update vertex buffer
        vertexBuffer.put(vertexData);
        vertexBuffer.position(0);
    }
}
