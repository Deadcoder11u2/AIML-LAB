import java.util.*;
import java.io.*;;

public class B {
	static int N;
	static HashMap<String, Boolean> mp;
	public static void main(String args[]) {
		N = Integer.parseInt(args[0]);
		mp = new HashMap<>();
		solve();
	}

	public static void solve() {
		PriorityQueue<State> q = new PriorityQueue<>();
		q.add(new State(N, N, 0, 0, true, null));
		State ans = null;
		while(!q.isEmpty()) {
			State current = q.poll();
			String curState = current.toString();
			if(current.finished()) {
				ans = current;
				break;
			}
			if(mp.get(curState) != null) {
				continue;
			}
			if(current.isLeft) {
				if(current.lc >= 2) {
					q.add(new State(current.lc-2, current.lm, current.rc+2, current.rm, !current.isLeft, current));
				}
				
				if(current.lm >= 2) {
					q.add(new State(current.lc, current.lm-2, current.rc, current.rm+2, !current.isLeft, current));
				}

				if(current.lc >= 1 && current.lm >= 1) {
					q.add(new State(current.lc-1, current.lm-1, current.rc+1, current.rm+1, !current.isLeft, current));
				}
			}
			else {
				if(current.rc >= 2) {
					q.add(new State(current.lc+2, current.lm, current.rc-2, current.rm, !current.isLeft, current));
				}

				if(current.rm >= 2) {
					q.add(new State(current.lc, current.lm+2, current.rc, current.rm-2, !current.isLeft, current));
				}

				if(current.rc >= 1 && current.rm >= 1) {
					q.add(new State(current.lc+1, current.lm+1, current.rc-1, current.rm-1, !current.isLeft, current));
				}

				if(current.rc >= 1) {
					q.add(new State(current.lc+1, current.lm, current.rc-1, current.rm, !current.isLeft, current));
				}

				if(current.rm >= 1) {
					q.add(new State(current.lc, current.lm+1, current.rc, current.rm-1, !current.isLeft, current));
				}
				mp.put(current.toString(), true);
			}
		}
		ArrayList<State> ansList = new ArrayList<>();
		while(ans != null) {
			ansList.add(ans);
			ans=ans.prev;
		}
		Collections.reverse(ansList);
		ansList.forEach( i -> {
			System.out.println("M".repeat(i.lm)+"_".repeat(N-i.lm) + " " +
			"C".repeat(i.lc)+"_".repeat(N-i.lc) + " ================== " +
			"M".repeat(i.rm)+"_".repeat(N-i.rm) + " " + "C".repeat(i.rc)+"_".repeat(N-i.rc));
		});
	}

	static class State implements Comparable<State>{
		int lc, lm, rc, rm;
		boolean isLeft;
		State prev;
		int depth = 0;

		public State() {
		}

		public State(int lc, int lm, int rc, int rm, boolean isLeft, State prev) {
			this.lc = lc;
			this.lm = lm;
			this.rc = rc;
			this.rm = rm;
			this.isLeft = isLeft;
			this.prev = prev;
			if(prev != null)
				this.depth = prev.depth + 1;
		}


		@Override public String toString() {
			return "{" + lc + "," + lm + "," + rc + "," + rm + " " + isLeft + "}";
		}

		@Override public int compareTo(State o) {
			return Double.compare(g() + h(), o.g() + o.h());
		}

		public void setDepth(int depth) {
			this.depth = depth;
		}

		public int g() {
			return depth;
		}

		public double h() {
			return (lc + rc) / 2.0;
		}

		public boolean finished() {
			return this.lc + this.lm == 0;
		}
	}
}
