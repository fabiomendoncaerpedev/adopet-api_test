package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {
    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacao;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private Adocao adocao;
    @Mock
    private Tutor tutor;
    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("deve permitir que a adoção prossiga com sucesso")
    void cenarioPositivo() {
        List<Adocao> adocaoList = new ArrayList<>();
        adocaoList.add(adocao);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocaoList);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getStatus()).willReturn(StatusAdocao.APROVADO);

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("deve não permitir que a adoção prossiga, informando que tutor chegou ao máximo de 5 adoções")
    void cenarioNegativo() {
        List<Adocao> adocaoList = new ArrayList<>();
        adocaoList.add(adocao);
        adocaoList.add(adocao);
        adocaoList.add(adocao);
        adocaoList.add(adocao);
        adocaoList.add(adocao);
        adocaoList.add(adocao);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocaoList);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getStatus()).willReturn(StatusAdocao.APROVADO);

        assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}