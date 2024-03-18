using System.Collections;
using UnityEngine;

public class TrapSpeed : MonoBehaviour
{
    [SerializeField] private bool isActivated = true;
    [SerializeField] public float slowness = 200f;
    [SerializeField] public float nerfTimer = 3f;

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "Player" && isActivated) {
            isActivated = false;
            StartCoroutine(ActiveTrap(collision));
        }
    }

    // coroutine qui permet de faire se réaliser un fonction en parallèle indépendament des autres qui fonctionnent déjà
    private IEnumerator ActiveTrap (Collider2D collision) {
        Speed playerSpeed = collision.gameObject.GetComponent<Speed>();
        if (playerSpeed != null)
        {
            playerSpeed.TakeNerf(slowness);
            yield return new WaitForSecondsRealtime(nerfTimer);
            playerSpeed.ResetSpeed();
        }
        isActivated = true;
    }

    public bool isTrapActivated  () {
        return isActivated;
    }
}
