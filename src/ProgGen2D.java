import java.util.ArrayList;

public class ProgGen2D {
  static int maxlen = 5;

  public static void main(String[] args) {
    recurse(new Cmd[maxlen], 0, 0);
    printRegistry();
  }

  public static void run() {
    recurse(new Cmd[maxlen], 0, 0);
    condenseRegistry();
  }

  @SuppressWarnings("unchecked")
  public static ArrayList<Cmd[]>[] registry = new ArrayList[maxlen + 1];
  public static ArrayList<Cmd[]> condensed = new ArrayList<Cmd[]>();

  public static void printRegistry() {
    for (int i = 0; i < registry.length; i++) {
      if (registry[i].size() <= 1000) {
        for (Cmd[] cmds : registry[i]) {
          System.out.println(Util.c2s(cmds));
        }
        System.out.println(registry[i].size());
      }
    }
  }

  public static void condenseRegistry() {
    for (int i = 0; i < registry.length; i++) {
      for (Cmd[] cmds : registry[i]) {
        condensed.add(cmds);
      }
    }
  }

  public static void register(Cmd[] cmds, int loc) {
    Cmd[] clone = cmds.clone();
    if (registry[loc] == null) {
      registry[loc] = new ArrayList<Cmd[]>();
    }
    registry[loc].add(clone);
  }

  public static void recurse(Cmd[] cmds, int loc, int nest) {
    if (nest == 0) {
      register(cmds, loc);
    }
    if (loc == cmds.length) {
      return;
    } else if (loc == 0) {
      cmds[loc] = Cmd.R;
      recurse(cmds, loc + 1, nest);
      cmds[loc] = Cmd.L;
      recurse(cmds, loc + 1, nest);
      cmds[loc] = Cmd.I;
      recurse(cmds, loc + 1, nest);
      cmds[loc] = Cmd.D;
      recurse(cmds, loc + 1, nest);
      cmds[loc] = Cmd.W;
      recurse(cmds, loc + 1, nest + 1);
    } else {
      cmds[loc] = Cmd.R;
      recurse(cmds, loc + 1, nest);
      cmds[loc] = Cmd.L;
      recurse(cmds, loc + 1, nest);
      cmds[loc] = Cmd.D;
      recurse(cmds, loc + 1, nest);
      cmds[loc] = Cmd.I;
      recurse(cmds, loc + 1, nest);
      cmds[loc] = Cmd.W;
      recurse(cmds, loc + 1, nest + 1);
      if (nest > 0) {
        cmds[loc] = Cmd.E;
        recurse(cmds, loc + 1, nest - 1);
      }
      cmds[loc - 1] = null;
    }
    cmds[loc] = null;
  }
}
