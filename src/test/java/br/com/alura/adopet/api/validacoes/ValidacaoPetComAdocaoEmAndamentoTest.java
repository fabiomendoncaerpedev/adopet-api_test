package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {
    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacao;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deverá permitir seguir com a adoção")
    void cenarioPositivo() {
        BDDMockito.given(
                adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Deverá levantar exceção avisando que Pet já está em processo de adoção")
    void cenarioNegativo() {
        BDDMockito.given(
                adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(true);

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

        Assertions.assertEquals(ex.getMessage(), "Pet já está aguardando avaliação para ser adotado!");
    }
}