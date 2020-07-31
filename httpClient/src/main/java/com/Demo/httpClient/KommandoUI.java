package com.Demo.httpClient;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import modell.Model;
import services.GetService;

public class KommandoUI {

	private GetService get;
	private String BaseUrl = "https://jsonplaceholder.typicode.com/";
	private Scanner scanner;
	String[] Optionen = { "alle", "einen", "filteren bei Title ", "Sort bei Title", "aSynchron" };

	private  int testvar;

	Runnable printItOut = () -> System.out.println("you cant!"+ ++testvar);
	
	public KommandoUI() {
		get = new GetService();
		scanner = new Scanner(System.in);
		
		printItOut.run();
	}

	public  void Printer(List<Model> list) {

		list.forEach(System.out::println);
		testvar = 5;
		System.out.println(testvar);
	}

	
	
	public void run() throws Exception {

		menu();

		while (true) {
			System.err.println("was wollen Sie machen?");
			Thread.sleep(400);
			System.out.print("  für Menu typen Sie menu ein.");
			Thread.sleep(400);
			System.out.print("..... oder! für schlissen typen Sie exit ein.\n");
			Thread.sleep(2000);
			var eingabe = scanner.nextLine();
			if (eingabe.equals("exit")) {
				System.err.println("byyyyyyyyyy!!");
				break;
			} else {

				switch (eingabe) {
				case "1":
					get.getAll(BaseUrl + "posts").forEach(System.out::println);
					break;
				case "2":
					System.err.println("Bitte ID eingeben:\n");
					System.out.println(get.getByID(BaseUrl + "post/" + getInput()));
					System.err.println("\n");
					break;

				case "3":
					System.err.println("Bitte Suchwort eingeben:\n");
					filter(getInput());

					System.out.println("noch suchen? y/n\n");
					if (getInput().contentEquals("y")) {
						System.err.println("Bitte Suchwort eingeben:\n");
						filter(getInput());
					} else {
						break;
					}
					break;
				case "4":
					sortByTitle(get.getAll(BaseUrl + "posts")).forEach(System.out::println);
					break;
				case "5":
					get.readAsynchronously(BaseUrl + "posts",  this,printItOut);
					System.out.println("from main" + testvar);

					break;
				case "menu":
					menu();
					break;
				default:
					menu();
					break;
				}

			}

		}

	}

	private void filter(String suchwort) throws Exception {

		List<Model> posts = get.getAll(BaseUrl + "posts");
		int ergebnisse = 0;
		for (Model model : posts) {
			if (model.getTitle().contains(suchwort)) {
				System.out.println(model);
				ergebnisse++;
			}
		}

		if (ergebnisse == 0) {
			System.out.println("leider keine Ergibnesse\n");

		} else {
			System.out.println(ergebnisse + " Ergibnesse wurde gefunden");
		}

	}

	private List<Model> sortByTitle(List<Model> list) {

		Collections.sort(list, new Comparator<Model>() {
			{
			}

			@Override
			public int compare(Model o1, Model o2) {
				// TODO Auto-generated method stub
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});

		return list;

	}

	private String getInput() {

		return scanner.nextLine();
	}

	private void menu() {

		System.out.println("da sind Ihre Optionen;\n");

		for (int i = 0; i < Optionen.length; i++) {

			System.out.println(i + 1 + "- " + Optionen[i] + "\n");
		}
	}
}
