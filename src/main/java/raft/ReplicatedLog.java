package raft;

import java.util.List;
import java.util.function.Consumer;

import io.microraft.statemachine.StateMachine;

public class ReplicatedLog implements StateMachine {

	@Override
	public Object runOperation(long commitIndex, Object operation) {
		return null;
	}

	@Override
	public Object getNewTermOperation() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getNewTermOperation'");
	}

	@Override
	public void installSnapshot(long arg0, List<Object> arg1) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'installSnapshot'");
	}

	@Override
	public void takeSnapshot(long arg0, Consumer<Object> arg1) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'takeSnapshot'");
	}
}
