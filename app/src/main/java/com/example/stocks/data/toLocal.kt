package com.example.stocks.data

import com.example.stocks.data.local.LocalHolding
import com.example.stocks.data.modal.Holding
import com.example.stocks.data.network.NetworkHolding


// Local to Network
fun LocalHolding.toNetwork() = NetworkHolding(
    avgPrice = avgPrice,
    close = close,
    ltp= ltp,
    quantity = quantity,
    symbol = symbol
)

fun List<LocalHolding>.toNetwork() = map(LocalHolding::toNetwork)


fun NetworkHolding.toLocal() = LocalHolding(
    id = null,
    avgPrice = avgPrice,
    close = close,
    ltp= ltp,
    quantity = quantity,
    symbol = symbol
)

@JvmName("networkToLocal")
fun List<NetworkHolding>.toLocal() = map(NetworkHolding::toLocal)


// Note: JvmName is used to provide a unique name for each extension function with the same name.
// Without this, type erasure will cause compiler errors because these methods will have the same
// signature on the JVM.
@JvmName("localToExternal")
fun List<LocalHolding>.toExternal() = map(LocalHolding::toExternal)


// Local to External
fun LocalHolding.toExternal() = Holding(
    id = id,
    avgPrice = avgPrice,
    close = close,
    ltp= ltp,
    quantity = quantity,
    symbol = symbol
)