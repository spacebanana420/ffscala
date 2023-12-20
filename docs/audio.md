These functions handle encoder-specific settings and parameters for audio encoders.

---

```scala
def aac_setCoder(coder: String): List[String]
```
Sets the coder for AAC.

#### Supported:
* anmr
* twoloop
* fast

```scala
def opus_setVBR(mode: String): List[String]
```
Sets the VBR mode for the opus encoder.

#### Supported modes:
* on
* off
* constrained
