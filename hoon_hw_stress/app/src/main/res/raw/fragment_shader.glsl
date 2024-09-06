#version 100
precision mediump float;

void main() {
    vec2 pos = gl_FragCoord.xy;
    float val = 0.0;

    // 반복적인 수학 연산을 수행, 부하량 조절 필요시 for문 내 반복횟수 조절
    for (int i = 0; i < 100; i++) {
        val += sin(pos.x * pos.y * 0.01 * float(i)) * cos(pos.x * 0.1 * float(i));
    }

    // 색상 계산
    float r = sin(val) * 0.5 + 0.5;
    float g = cos(val) * 0.5 + 0.5;
    float b = sin(val * 0.5) * 0.5 + 0.5;

    gl_FragColor = vec4(r, g, b, 1.0);
}