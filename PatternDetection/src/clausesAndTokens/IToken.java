package clausesAndTokens;

import java.util.HashMap;

public abstract class IToken {

	public abstract HashMap<FeatureType, IToken> getTokens();
//	public boolean matches( Clause<T extends this> clause );
}
