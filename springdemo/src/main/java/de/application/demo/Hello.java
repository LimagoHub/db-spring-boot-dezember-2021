package de.application.demo;

public class Hello implements AutoCloseable{


    private String message = "Hallo Welt";
    private Transformer transformer;

//    public void setTransformer(Transformer transformer) {
//        this.transformer = transformer;
//    }

//    public Hello() {
//
//        System.out.println("Hier ist Hello");
//        System.out.println(message);
//    }

    public Hello(Transformer transformer) {
        this.transformer = transformer;
        System.out.println("Hier ist Hello");
        System.out.println(transformer.transform(message));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void init() {
        System.out.println("Post Construct");
        System.out.println(transformer.transform(message));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hello{");
        sb.append("message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void close() throws Exception {
        System.out.println(transformer.transform("Und tschuess!"));
    }
}
