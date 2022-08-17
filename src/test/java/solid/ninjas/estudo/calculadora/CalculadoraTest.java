package solid.ninjas.estudo.calculadora;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static solid.ninjas.estudo.calculadora.Cargo.*;

enum Cargo {
  DESENVOLVEDOR(new DezOuVintePorCento()),
  DBA(new QuinzeOuVinteCincoPorCento()),
  TESTER(new QuinzeOuVinteCincoPorCento());

  private final RegraDeCalculo regraDeCalculo;

  Cargo(RegraDeCalculo regraDeCalculo) {
    this.regraDeCalculo = regraDeCalculo;
  }

  public RegraDeCalculo getRegra() {
    return regraDeCalculo;
  }
}

interface RegraDeCalculo {
  double calcula(Funcionario funcionario);
}

public class CalculadoraTest {
  @Test
  @DisplayName("Deve entrar na regra dezOuVintePorcento")
  void testeUm() {
    final var dev = new Funcionario(DESENVOLVEDOR, 2000);

    final var calculadoraDeSalario = new CalculadoraDeSalario();
    final var retornoCalculo = calculadoraDeSalario.calcula(dev);
    assertEquals(400, retornoCalculo);
  }

  @Test
  @DisplayName("Deve entrar na regra quinzeOuVinteCincoPorcento")
  void testeDois() {
    final var dba = new Funcionario(DBA, 2000);

    final var calculadoraDeSalario = new CalculadoraDeSalario();
    final var retornoCalculo = calculadoraDeSalario.calcula(dba);
    assertEquals(300, retornoCalculo);
  }
}

class CalculadoraDeSalario {

  public double calcula(Funcionario funcionario) {
    if (DESENVOLVEDOR.equals(funcionario.getCargo())) {
      return DESENVOLVEDOR.getRegra().calcula(funcionario);
    }
    if (DBA.equals(funcionario.getCargo()) ||
        TESTER.equals(funcionario.getCargo())) {
      return DBA.getRegra().calcula(funcionario);
    }
    throw new RuntimeException("funcionario invalido");
  }

}

class DezOuVintePorCento implements RegraDeCalculo {

  @Override
  public double calcula(Funcionario funcionario) {
    if (funcionario.getSalarioBase() > 3000.0) {
      return funcionario.getSalarioBase() * 0.10;
    } else {
      return funcionario.getSalarioBase() * 0.20;
    }
  }
}

class QuinzeOuVinteCincoPorCento implements RegraDeCalculo {

  @Override
  public double calcula(Funcionario funcionario) {
    if (funcionario.getSalarioBase() > 3000.0) {
      return funcionario.getSalarioBase() * 0.20;
    } else {
      return funcionario.getSalarioBase() * 0.15;
    }
  }
}

@Getter
@Setter
@AllArgsConstructor
class Funcionario {
  private Enum<Cargo> cargo;
  private double salarioBase;
}