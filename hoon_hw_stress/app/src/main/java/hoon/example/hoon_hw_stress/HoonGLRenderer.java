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

    private int program;
    private int uResolutionLocation;
    private Context mContext;

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

        // 셰이더 프로그램 생성 및 링크
        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);

        // uResolution 유니폼의 위치를 가져옴
        uResolutionLocation = GLES20.glGetUniformLocation(program, "uResolution");
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // 화면을 지우고 셰이더 프로그램을 적용
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(program);

        // 화면 크기를 셰이더로 전달
//        GLES20.glUniform2f(uResolutionLocation, (float)width, (float)height);

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
        // 간단한 사각형을 화면에 그려서 셰이더 적용
        float[] vertices = {
                -1.0f, 1.0f,
                -1.0f, -1.0f,
                1.0f, 1.0f,
                1.0f, -1.0f
        };

        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertices).position(0);

        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
