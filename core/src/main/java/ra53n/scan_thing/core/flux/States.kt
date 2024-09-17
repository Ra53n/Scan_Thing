package ra53n.scan_thing.core.flux

sealed class States<TYPE>

class Pending<TYPE> : States<TYPE>()
class Success<TYPE>(val result: TYPE) : States<TYPE>()
class Fail<TYPE>(val error: Throwable?) : States<TYPE>()