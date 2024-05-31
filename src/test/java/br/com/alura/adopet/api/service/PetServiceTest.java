package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @InjectMocks
    private PetService service;
    @Mock
    private PetRepository petRepository;
    @Mock
    private Abrigo abrigo;
    @Mock
    private CadastroPetDto dto;

    @Test
    @DisplayName("deve chamar o Pet Repository no buscar pets disponiveis")
    void cenarioBuscarPetsDisponiveis() {
        service.buscarPetsDisponiveis();

        then(petRepository).should().findAllByAdotadoFalse();
    }

    @Test
    @DisplayName("deve cadastrar o pet")
    void cenarioCadastroDePet() {
        service.cadastrarPet(abrigo, dto);

        then(petRepository).should().save(new Pet(dto, abrigo));
    }
}