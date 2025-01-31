package ua.hiikkolab.noteune.appui.impl;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import ua.hiikkolab.noteune.appui.contract.Renderable;

public class Renderer implements Renderable {
  private Renderer() {
    AnsiConsole.systemInstall();
    System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    //AnsiConsole.out().println(Ansi.ansi().fg(30).bg(43).a("header" + "\n" + "content").reset());

  }
  public static Renderer instance() {
    return Instance.INST;
  }
  private static class Instance {
    public static final Renderer INST = new Renderer();
  }
  @Override
  public void render(String header, String content, int fg, int bg) {
    AnsiConsole.out().print(Ansi.ansi().fg(fg).bg(bg).a(
        /*Next comment - for debug usage*//*header +*/ " \n" + content));
    AnsiConsole.out().println();
  }
}
