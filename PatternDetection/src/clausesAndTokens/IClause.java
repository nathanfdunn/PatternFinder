package clausesAndTokens;

public abstract class IClause<T extends IToken> {

	public abstract boolean matches(T token);
	
}
