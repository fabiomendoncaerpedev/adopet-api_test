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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {
    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacao;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private Tutor tutor;
    @Mock
    private Adocao adocao;
    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deve permitir que adoção seja realizada")
    void cenarioPositivo() {
        BDDMockito.given(adocaoRepository.findAll()).willReturn(Collections.emptyList());
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Deveria não permitir e avisar que o tutor já possui outra adoção em andamento")
    void cenarioNegativo() {
        String expectedMessage = "Tutor já possui outra adoção aguardando avaliação!";
        List<Adocao> adocaoList = new ArrayList<>();
        adocaoList.add(adocao);
        adocaoList.add(adocao);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocaoList);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getStatus()).willReturn(StatusAdocao.AGUARDANDO_AVALIACAO);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        ValidacaoException ex = assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

        assertEquals(ex.getMessage(), expectedMessage);
    }
}