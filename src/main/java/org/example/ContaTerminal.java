package org.example;

import java.util.Locale;
import java.util.Scanner;

public class ContaTerminal {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		ContaTerminal terminal = new ContaTerminal();
		terminal.init();
	}

	public void init() {
		String cliente = null;
		String agencia = null;
		Integer numero = null;
		Float saldo = null;
		String line;
		try (Scanner scanner = new Scanner(System.in)) {
			while (numero == null || agencia == null || cliente == null || saldo == null) {
				try {
					if (cliente == null) {
						System.out.println("Informe o nome do cliente:");
						line = scanner.nextLine();
						ContaValidator.validarCliente(line);
						cliente = line;
					}
					if (agencia == null) {
						System.out.println("Informe o número da agência (ex: 999-9):");
						line = scanner.nextLine();
						ContaValidator.validarAgencia(line);
						agencia = line;
					}
					if (numero == null) {
						System.out.println("Informe o número da conta (ex: 9999):");
						line = scanner.nextLine();
						ContaValidator.validarNumero(line);
						numero = Integer.parseInt(line);
					}
					if (saldo == null) {
						System.out.println("Informe o saldo do cliente (ex: 9999.99):");
						line = scanner.nextLine();
						ContaValidator.validarSaldo(line);
						saldo = Float.parseFloat(line);
					}
				} catch (ContaException e) {
					System.out.println("[ERRO] " + e.getMessage());
				}
			}
		}
		Conta conta = Conta.of(numero, agencia, cliente, saldo);
		System.out.printf("Olá %s, obrigado por criar uma conta em nosso banco, " +
						"sua agência é %s, " +
						"conta %d " +
						"e seu saldo %.2f já está disponível para saque.",
				conta.getCliente(),
				conta.getAgencia(),
				conta.getNumero(),
				conta.getSaldo()
		);
	}

}

class Conta {

	private final Integer numero;

	private final String agencia;

	private final String cliente;

	private final Float saldo;

	private Conta(Integer numero, String agencia, String cliente, Float saldo) {
		ContaValidator.validarNumero(numero);
		ContaValidator.validarAgencia(agencia);
		ContaValidator.validarCliente(cliente);
		ContaValidator.validarSaldo(saldo);
		this.numero = numero;
		this.agencia = agencia.trim();
		this.cliente = cliente.trim();
		this.saldo = saldo;
	}

	public static Conta of(Integer numero, String agencia, String cliente, Float saldo) {
		return new Conta(numero, agencia, cliente, saldo);
	}

	public Integer getNumero() {
		return numero;
	}

	public String getAgencia() {
		return agencia;
	}

	public String getCliente() {
		return cliente;
	}

	public Float getSaldo() {
		return saldo;
	}

}

class ContaValidator {

	private ContaValidator() {
	}

	public static void validarNumero(Integer numero) {
		if (numero == null) {
			throw new ContaException("O numero da conta nao pode ser nulo.");
		}
		if (numero <= 0) {
			throw new ContaException("O numero da conta nao pode ser menor ou igual a zero.");
		}
	}

	public static void validarNumero(String numero) {
		try {
			validarNumero(Integer.parseInt(numero));
		} catch (NumberFormatException e) {
			throw new ContaException("O numero da conta e' invalido.");
		}
	}

	public static void validarAgencia(String agencia) {
		if (agencia == null) {
			throw new ContaException("O numero da agencia nao pode ser nulo.");
		}
		if (agencia.isBlank()) {
			throw new ContaException("O numero da agencia deve ser informado.");
		}
		if (!agencia.trim().matches("\\d{3}-\\d")) {
			throw new ContaException("O numero da agencia informado nao esta no formato 999-9.");
		}
	}

	public static void validarCliente(String cliente) {
		if (cliente == null) {
			throw new ContaException("O nome do cliente nao pode ser nulo.");
		}
		if (cliente.isBlank()) {
			throw new ContaException("O nome do cliente nao pode ser vazio.");
		}
	}

	public static void validarSaldo(Float saldo) {
		if (saldo == null) {
			throw new ContaException("O saldo nao pode ser nulo.");
		}
		if (saldo <= 0) {
			throw new ContaException("O saldo nao pode ser menor ou igual a zero.");
		}
	}

	public static void validarSaldo(String saldo) {
		try {
			validarSaldo(Float.parseFloat(saldo));
		} catch (NumberFormatException e) {
			throw new ContaException("O saldo informado e' invalido.");
		}
	}

}

class ContaException extends RuntimeException {

	public ContaException() {
	}

	public ContaException(String message) {
		super(message);
	}

}