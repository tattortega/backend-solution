package co.com.sofka.questions.model;

public class ResponseDTO {
    private String mensaje;
    private Boolean estado;


    public ResponseDTO(String mensaje, Boolean estado) {
        this.mensaje = mensaje;
        this.estado = estado;
    }

    public ResponseDTO() {

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
