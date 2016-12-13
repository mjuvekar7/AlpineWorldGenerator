/*
 * Copyright 2016 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

import java.util.Map;

public class SnowMoundRasterizer implements WorldRasterizer {
    Block snow;

    @Override
    public void initialize() {
        snow = CoreRegistry.get(BlockManager.class).getBlock("Core:Snowball");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        SnowMoundFacet snowMoundFacet = chunkRegion.getFacet(SnowMoundFacet.class);

        for (Map.Entry<BaseVector3i, SnowMound> entry : snowMoundFacet.getWorldEntries().entrySet()) {
            Vector3i centerMoundPosition = new Vector3i(entry.getKey());
            Vector3i extent = entry.getValue().getExtent();
            Region3i moundRegion = Region3i.createFromCenterExtents(centerMoundPosition, extent);

            for (Vector3i newBlockPosition : moundRegion) {
                if (chunkRegion.getRegion().encompasses(newBlockPosition)) {
                    chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), snow);
                }
            }
        }
    }
}
