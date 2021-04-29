    #type vertex
    #version 330 core
    layout (location=0) in vec3 aPos; //atribute position
    layout (location=1) in vec4 aColor;


    uniform mat4 uProjection;
    uniform mat4 uView;
    uniform sampler2D TEX_Sampler;

    out vec4 fColor; //fragment shader


    void main()
    {
        fColor = aColor;
        gl_Position = uProjection * uView * vec4(aPos, 1.0);
    }

    #type fragment
    #version 330 core


    in vec4 fColor;


    out vec4 color;

    void main()
    {
        color = fColor;
    }