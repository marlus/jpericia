package org.jpericia.common.entity.pericia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.jpericia.common.entity.AbstractEntity;


@Entity
@Table(name="anexo")
@SequenceGenerator(name = "anexo_sequence", sequenceName = "anexo_id_seq")
public class Anexo extends AbstractEntity {

	private static final long serialVersionUID = -426381767405280311L;

	private Long codigo;
	
	private Pericia pericia;
	
	private String titulo;
	
	private String texto;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "anexo_sequence")
	@Column(name="codigo")
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@ManyToOne()
	@JoinColumn(name="codigoPericia", referencedColumnName="codigo", nullable=false)
	public Pericia getPericia() {
		return pericia;
	}

	public void setPericia(Pericia pericia) {
		this.pericia = pericia;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Anexo other = (Anexo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
