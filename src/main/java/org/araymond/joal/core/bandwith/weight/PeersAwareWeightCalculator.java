package org.araymond.joal.core.bandwith.weight;

import org.araymond.joal.core.bandwith.Peers;

/**
 * Allocates higher weights to torrents with more leechers.
 */
public class PeersAwareWeightCalculator {
    public double calculate(final Peers peers, final long torrentSize, final long uploaded) {
        final double leechersRatio = peers.getLeechersRatio();
        return (peers.getSeeders() == 0 || leechersRatio == 0 || uploaded > torrentSize)
                ? 0.0
                : leechersRatio * 100 * leechersRatio * peers.getLeechers();
    }
}
