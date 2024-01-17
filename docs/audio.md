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

---

```scala
def opus_setVBR(mode: String): List[String]
```
Sets the VBR mode for the opus encoder.

#### Supported modes:
* on
* off
* constrained

---

```scala
def opus_setApplication(mode: String): List[String]
```
Sets the application type for opus encoding. This tells opus what usecase is ideal, and changes some encoding configurations.

#### Supported modes:
* voip
* audio
* lowdelay

---

```scala
def flac_lpcAlgorithm(mode: String): List[String]
```

Sets the LPC algorithm for the flac encoder.

#### Supported modes:
* none
* fixed
* levinson
* cholesky

---

```scala
def flac_predictionMethod(mode: String): List[String]
```

Sets the prediction order method for the flac encoder.

#### Supported modes:
* estimation
* 2level
* 4level
* 8level
* search
* log

---

```scala
def flac_decorrelation(mode: String): List[String]
```

Sets the stereo decorrelation mode for the flac encoder.

#### Supported modes:
* auto
* indep
* left_side
* right_side
* mid_side
