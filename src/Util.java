
public class Util {
  public static void printCharArr(char[][] ca) {
    for (int i = 0; i < ca.length; i++) {
      for (int j = 0; j < ca[0].length; j++) {
        System.out.print(ca[i][j]);
      }
      System.out.println();
    }
  }

  public static String c2s(Cmd[] cmds) {
    String res = "";
    for (int i = 0; i < cmds.length && cmds[i] != null; i++) {
      res += cmds[i].toString();
    }
    return res;
  }

  public static Cmd[] s2c(String s) {
    char[] chars = s.toCharArray();
    Cmd[] res = new Cmd[chars.length];
    for (int i = 0; i < chars.length; i++) {
      for (Cmd cmd : Cmd.values()) {
        if (cmd.c == chars[i]) {
          res[i] = cmd;
        }
      }
    }
    return res;
  }
}
