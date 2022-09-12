package com.adversegecko3.ezdnschanger

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class DNSViewModel : ViewModel() {
    val alItems = mutableStateListOf(
        DNS(
            "AdGuard",
            "dns.adguard.com"
        ),
        DNS(
            "1.1.1.1",
            "1dot1dot1dot1.cloudflare-dns.com"
        ),
        DNS(
            "8.8.8.8",
            "dns.google"
        )
    )

    fun updateDNS(pos: Int, dns: DNS) {
        alItems[pos] = dns
    }

    fun addDNS(dns: DNS) {
        alItems.add(dns)
    }

    fun deleteDNS(dns: DNS) {
        alItems.remove(dns)
    }
}
