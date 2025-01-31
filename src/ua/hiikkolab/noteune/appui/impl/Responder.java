package ua.hiikkolab.noteune.appui.impl;

import java.util.Scanner;
import ua.hiikkolab.noteune.appui.contract.Responsible;

public class Responder implements Responsible {

  private Responder(){

  }
  public static Responder instance() {
    return Responder.Instance.INST;
  }
  private static class Instance {
    public static final Responder INST = new Responder();
  }
  @Override
  public String getInput() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
}
