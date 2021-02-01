package nebula.dependencycache.builder

import groovy.transform.ToString
import groovy.transform.TupleConstructor

@TupleConstructor
@ToString
class CacheLayoutMapping {
    String modulesVersion
    String filesVersion
    String metadataVersion
}
