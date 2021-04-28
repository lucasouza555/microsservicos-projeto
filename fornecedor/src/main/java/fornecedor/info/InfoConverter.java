package fornecedor.info;

import fornecedor.info.dto.InfoSaidaDTO;
import fornecedor.info.modelo.InfoFornecedor;

public class InfoConverter {

	public static InfoSaidaDTO toDto(InfoFornecedor entidade) {
		InfoSaidaDTO dto = new InfoSaidaDTO();
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setEstado(entidade.getEstado());
		dto.setEndereco(entidade.getEndereco());
		return dto;
	}

}
