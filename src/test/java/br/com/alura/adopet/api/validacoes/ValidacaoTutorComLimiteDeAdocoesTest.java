package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {
    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacao;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("deve não permitir que a adoção prossiga, informando que tutor chegou ao máximo de 5 adoções")
    void cenarioNegativo() {
        BDDMockito.given(
                adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)
        ).willReturn(6);

        assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}