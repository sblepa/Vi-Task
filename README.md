# ViMarvel

### ImageFileDownloader

The way this class disc caching works is it serializes the memory cache into the disc on each update of the cache, it fetches
the disc cache once when the app loads, there is a more optimized solution to have a different cache for the disc (with a larges size and its own eviction mechanism)
but I left this implementation as its simpler

We assume each url image is unique and final, meaning the image of a url can't be updated, if we want to set a new image we'll create a new url for that

### Disclaimers

* many of the boilerplate logic here could be moved to base classes, it wasn't done here as we have at most two instances of a base class
