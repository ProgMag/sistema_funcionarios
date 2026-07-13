# Sistema de Funcionários — Gerentes e Estagiários

Projeto de fixação do módulo **Classes Abstratas e Interfaces**, em Java.

## Objetivo

Modelar uma hierarquia de funcionários onde cada tipo (Gerente, Estagiário) calcula
imposto, bônus e salário final de forma diferente, mas todos compartilham um
contrato comum — sem forçar comportamentos que não fazem sentido pra todo mundo
(ex: nem todo funcionário recebe bônus).

## Conceitos aplicados

- **Interface** como contrato de capacidade (`Tributavel`, `Bonificavel`)
- **Método `default`** para reaproveitar lógica comum sem duplicar código
- **Classe abstrata** como molde de identidade comum (`Funcionario`)
- **Método abstrato** para forçar cada subclasse a implementar sua própria regra
- **Polimorfismo** — o loop na `Main` trata todos como `Funcionario`, mas cada um
  executa sua própria versão de `exibirFicha()` e `calcularSalarioFinal()`
- **`instanceof` com pattern matching** — identifica em tempo de execução quem
  também implementa `Bonificavel`, sem precisar de cast manual
- **Princípio da Segregação de Interfaces (SOLID)** — `Bonificavel` foi separado
  de `Funcionario` justamente porque nem todo funcionário recebe bônus (o
  `Estagiario` não implementa essa interface)

## Estrutura

```
Tributavel (interface)
   └── calcularImposto(double)        [abstrato]
   └── calcularSalarioLiquido(double) [default — usa calcularImposto()]

Bonificavel (interface)
   └── calcularBonus(double)          [abstrato]

Funcionario (classe abstrata) implements Tributavel
   ├── Gerente implements Bonificavel
   └── Estagiario
```

## Interfaces

```java
public interface Tributavel {
    double calcularImposto(double salario);

    default double calcularSalarioLiquido(double salario) {
        double imposto = calcularImposto(salario);
        return salario - imposto;
    }
}
```

```java
public interface Bonificavel {
    double calcularBonus(double salario);
}
```

## Classe abstrata

```java
public abstract class Funcionario implements Tributavel {

    private String nome;
    private double salarioBase;

    public Funcionario(String nome, double salario) {
        this.nome = nome;
        this.salarioBase = salario;
    }

    public String getNome() {
        return nome;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    void exibirFicha(){
        System.out.printf("""
                Nome: %s
                Salário: %.2f
                """, nome, salarioBase);
    }

    abstract double calcularSalarioFinal();
}
```

## Subclasses

**Gerente** — imposto de 15%, bônus de 20% sobre o salário base:

```java
public class Gerente extends Funcionario implements Bonificavel {

    public Gerente(String nome, double salario) {
        super(nome, salario);
    }

    @Override
    public double calcularImposto(double salario) {
        return salario * 0.15;
    }

    @Override
    public double calcularBonus(double salario) {
        return salario * 0.20;
    }

    @Override
    public double calcularSalarioFinal() {
        double salarioLiquido = calcularSalarioLiquido(getSalarioBase());
        double bonus = calcularBonus(getSalarioBase());
        return bonus + salarioLiquido;
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.printf("""
                Salário final: %.2f
                """, calcularSalarioFinal());
    }
}
```

**Estagiario** — imposto de 0%, sem bônus (não implementa `Bonificavel`):

```java
public class Estagiario extends Funcionario {

    public Estagiario(String nome, double salario) {
        super(nome, salario);
    }

    @Override
    public double calcularImposto(double salario) {
        return 0;
    }

    @Override
    public double calcularSalarioFinal() {
        return calcularSalarioLiquido(getSalarioBase());
    }

    @Override
    void exibirFicha() {
        super.exibirFicha();
        System.out.printf("""
                Imposto: %.2f
                Salário final: %.2f
                """, calcularImposto(getSalarioBase()), calcularSalarioFinal());
    }
}
```

## Main

```java
import java.util.ArrayList;

public class Main {
    static void main() {

        Gerente gerente = new Gerente("Andresa", 5000);
        Gerente gerente1 = new Gerente("Ricardo", 6560);
        Estagiario estagiario = new Estagiario("Gui", 1000);

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(gerente);
        funcionarios.add(gerente1);
        funcionarios.add(estagiario);

        for (Funcionario funcionario : funcionarios) {
            System.out.print("\n");
            funcionario.exibirFicha();

            if (funcionario instanceof Bonificavel b) {
                double bonus = b.calcularBonus(funcionario.getSalarioBase());
                System.out.println(funcionario.getNome() + " recebeu uma bonificação de R$" + bonus);
            } else {
                System.out.println(funcionario.getNome() + " não recebeu uma bonificação");
            }
        }
    }
}
```

> **Nota sobre o `main()`:** a assinatura `static void main()` (sem `public`,
> sem `String[] args`) é válida a partir do JDK 25, graças ao JEP 512
> (*Compact Source Files and Instance Main Methods*). Em versões mais antigas
> do Java, seria necessário usar a forma tradicional
> `public static void main(String[] args)`.

## Como executar

```bash
javac Tributavel.java Bonificavel.java Funcionario.java Gerente.java Estagiario.java Main.java
java Main
```

## Pontos de aprendizado registrados durante o desenvolvimento

- O bônus do `Gerente` deve ser calculado sobre o **salário base**, não sobre
  o salário líquido — misturar essas duas bases foi o erro mais recorrente
  durante a implementação.
- Ao sobrescrever um método (`@Override`), a visibilidade **nunca pode ser
  reduzida** em relação à declaração original — só mantida ou ampliada.
- Métodos `default` de interface conseguem chamar métodos abstratos da própria
  interface e, em tempo de execução, o Java resolve automaticamente para a
  implementação da subclasse concreta (polimorfismo "por trás" do `default`).
- Mesmo quando um cálculo dá o mesmo resultado por coincidência (ex: salário
  final do estagiário = salário base, já que o imposto é 0%), é importante
  **calcular de verdade** via `calcularSalarioLiquido()` em vez de assumir o
  valor — isso evita bugs silenciosos se a regra de negócio mudar no futuro.

## Próximos passos

- Revisão aprofundada do tema (interfaces vs. classes abstratas, `default`,
  segregação de interfaces)
- Prova estilo universitário (10 questões, fácil → difícil)
- Retomar o projeto pendente: **Sistema de Processamento de Pagamentos (e-commerce)**
