package org.example.entity.horario;

import org.example.entity.sala.Sala;
import org.example.filme.Filme;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

public class Horario implements Serializable {

    private LocalDate data;
    private LocalTime horario;
    private Sala sala;

    public Horario(LocalDate data, LocalTime horario, Sala sala) {
        this.data = data;
        this.horario = horario;
        this.sala = sala;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horario)) return false;
        Horario horario1 = (Horario) o;
        return Objects.equals(getData(), horario1.getData()) && Objects.equals(getHorario(), horario1.getHorario()) && Objects.equals(getSala(), horario1.getSala());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData(), getHorario(), getSala());
    }

    @Override
    public String toString() {
        return "Horario{" +
                "data=" + data +
                ", horario=" + horario +
                ", sala=" + sala +
                '}';
    }
}
