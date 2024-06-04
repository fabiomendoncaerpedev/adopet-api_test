package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {
    @InjectMocks
    private AbrigoService service;
    @Mock
    private AbrigoRepository abrigoRepository;
    @Mock
    private CadastroAbrigoDto dto;
    @Mock
    private Optional<Abrigo> abrigo;
    @Mock
    private PetRepository petRepository;
    @Mock
    private Pet pet;

    @Test
    @DisplayName("deve chamar Abrigo Repository")
    void cenarioListarAbrigos() {
        service.listar();

        then(abrigoRepository).should().findAll();
    }

    @Test
    @DisplayName("deve impedir cadastro e avisar quando abrigo já cadastrado")
    void cadastrarAbrigo() {
        String expectedMessage = "Dados já cadastrados para outro abrigo!";
        BDDMockito.given(
                abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())
        ).willReturn(true);

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrar(dto));

        Assertions.assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    @DisplayName("deve chamar abrigo repository buscando pelo NOME quando informado um NOME")
    void listarTodosOsPetsDoAbrigoComNome() {
        String nome = "gatonildo";
        BDDMockito.given(
                abrigoRepository.findByNome(nome)
        ).willReturn(abrigo);

        service.listarPetsDoAbrigo(nome);

        then(abrigoRepository).should().findByNome(nome);
    }

    @Test
    @DisplayName("deve chamar abrigo repository buscando pelo ID quando informado um ID (número, porém encapsulado em string)")
    void listarTodosOsPetsDoAbrigoComID() {
        String id = "1";
        Long IDEmNumero = Long.parseLong(id);
        BDDMockito.given(
                abrigoRepository.findById(IDEmNumero)
        ).willReturn(abrigo);

        service.listarPetsDoAbrigo(id);

        then(abrigoRepository).should().findById(IDEmNumero);
    }
}